/* eslint-disable prettier/prettier */
import { forwardRef, Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { User } from './entities/user.entity';
import { ConfigModule } from '@nestjs/config';
import { UsersService } from './user.service';
import { UserController } from './user.controller';
import { CommonModule } from 'src/common/common.module';
import { AuthModule } from 'src/auth/auth.module';

@Module({
  imports: [
    TypeOrmModule.forFeature([User]), 
    CommonModule, 
    ConfigModule,
    forwardRef(() => AuthModule)
    ],
    controllers: [UserController],
    providers: [UsersService],
    exports: [UsersService, TypeOrmModule]
})
export class UserModule {}
