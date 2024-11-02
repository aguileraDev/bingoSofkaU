/*eslint-disable prettier/prettier*/
import { Injectable, UnauthorizedException } from '@nestjs/common';

import * as bcrypt from 'bcrypt';

import { LoginUserDto } from './dto/login.user.dto';

import { UsersService } from 'src/user/user.service';
import { HandleException } from 'src/common/helpers/handle.exception';

@Injectable()
export class AuthService {

  constructor(
    private readonly userService: UsersService,
    private readonly handleException: HandleException,

  ) { }

  async login(loginUserDto: LoginUserDto) {

    const { email, password } = loginUserDto;
    const loginPassword = password;

    try {

      const { password, ...user } = await this.userService.findOneUserForLogin(email);

      if (!bcrypt.compareSync(loginPassword, password)) throw new UnauthorizedException(`Invalid credentials`);

      return user;

    } catch (error) {
      this.handleException.checkCommonDatabaseExceptions(error);

    }
  }

}
