/*eslint-disable prettier/prettier*/

import{ User } from 'src/user/entities/user.entity';

import { PassportStrategy } from '@nestjs/passport';
import { ExtractJwt, Strategy } from 'passport-jwt';
import { JwtPayload } from '../interfaces/jwt.payload.interface';
import { Injectable } from '@nestjs/common';
import { UsersService } from 'src/user/user.service';
import { HandleException } from 'src/common/helpers/handle.exception';
import { ConfigService } from '@nestjs/config';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  constructor(
   
    @InjectRepository(User)
    private readonly userRepository:Repository<User>,
    private readonly userService: UsersService,
    private readonly configService: ConfigService,
    private readonly handleException: HandleException,
    
  ) {
    super({
      secretOrKey: process.env.JWT_SECRET,
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
    })
  }

   async validate(payload: JwtPayload): Promise<User> {
    const { id } = payload;
    const user = await this.userService.findOneForJwtValidate(id);
    return user;

  }
}
