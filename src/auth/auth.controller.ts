/*eslint-disable prettier/prettier*/
import { Controller, Post, Body } from '@nestjs/common';
import { AuthService } from './auth.service';

import { LoginUserDto } from './dto/login.user.dto';

import { CreateUserDto } from 'src/user/dto';
import { UsersService } from 'src/user/user.service';

@Controller('auth')
export class AuthController {
  constructor(
    private readonly userService: UsersService,
    private readonly authService: AuthService,
    ) {}

  @Post('register')
  create(@Body() createUserDto: CreateUserDto) {
    return this.userService.create(createUserDto);
    
  }

  @Post('login')
  signIn(@Body() loginUserDto: LoginUserDto){
    return this.authService.login( loginUserDto );
  }



}

