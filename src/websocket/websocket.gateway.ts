/* eslint-disable prettier/prettier */
import { MessageBody, SubscribeMessage, WebSocketGateway, WebSocketServer } from '@nestjs/websockets';
import { WebsocketService } from './websocket.service';
import { OnModuleInit } from '@nestjs/common';
import { Server, Socket } from 'socket.io';
import { User } from './interfaces/user.interface';

@WebSocketGateway({
  cors: {
    origin: '*', 
    methods: ['GET', 'POST'],
  },
})
export class WebsocketGateway implements OnModuleInit {
  
  private connectedUsers: User[] = [];
  private gamingUsers: User[] = [];
  
  constructor(private readonly websocketService: WebsocketService) { }

  @WebSocketServer()
  public server: Server;
  onModuleInit() {
    this.server.on('connection', (socket: Socket) => {
      console.log(`user connected: ${socket.id}`);}
  )}

  @SubscribeMessage('event:lobby')
  handleLobby(@MessageBody() data: User) {
    const userIsLogin = this.connectedUsers.find((user) => user.id === data.id);

    if (!userIsLogin) {
      this.connectedUsers.push(data);
    } else {
      console.log(`Usuario ${data.id} ya está en la app`);
    }

    this.server.emit('event:connected', this.connectedUsers);
  }

  @SubscribeMessage('event:logout')
  handleLogout(@MessageBody() data: User) {
    const index = this.connectedUsers.findIndex((user) => user.id === data.id);

    if (index !== -1) {
      this.connectedUsers.splice(index, 1);
    }
  }

  @SubscribeMessage('event:status')
  handleStatus(@MessageBody() data: { id: string; status: 'won' | 'loser' }) {
    const user = this.connectedUsers.find((user) => user.id === data.id);

    if (user) {
      switch (data.status) {
        case 'won':
          user.won = true;
          break;
        case 'loser':
          user.loser = true;
          break;
      }
    }
  }

  @SubscribeMessage('event:gaming')
  handleGaming(@MessageBody() data: User) {
    const user = this.gamingUsers.find((user) => user.id === data.id);
    if (!user) {
      this.gamingUsers.push(data);
    } else {
      user.won = false;
      user.loser = false;
    }
  

    this.server.emit('event:gameprogress', this.gamingUsers);
  }

  @SubscribeMessage('event:offgaming')
  handleOffGaming(@MessageBody() data: User) {
    const index = this.gamingUsers.findIndex((user) => user.id === data.id);

    if (index !== -1) {
      this.gamingUsers.splice(index, 1);
    }

    this.server.emit('event:gameprogress', this.gamingUsers);
  }

  @SubscribeMessage('event:button')
  handleButton(@MessageBody() data: any) {
    this.server.emit('event:button', data);
  }
}