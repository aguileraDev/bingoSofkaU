/* eslint-disable prettier/prettier */
import { WebSocketGateway, WebSocketServer } from '@nestjs/websockets';
import { WebsocketService } from './websocket.service';
import { OnModuleInit } from '@nestjs/common';
import { Server, Socket } from 'socket.io';

@WebSocketGateway()
export class WebsocketGateway implements OnModuleInit {
  constructor(private readonly websocketService: WebsocketService) { }

  @WebSocketServer()
  public server: Server;
  onModuleInit() {
    this.server.on('connection', (socket: Socket) => {
      console.log(`user connected: ${socket.id}`);}
  )}
}