/*eslint-disable prettier/prettier*/
import { forwardRef, Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { AuthService } from './auth.service';
import { AuthController } from './auth.controller';
import { CommonModule } from 'src/common/common.module';
import { PassportModule } from '@nestjs/passport';
import { JwtModule } from '@nestjs/jwt';

import { JwtStrategy } from './strategies/jwt.payload.strategy';
import { UserModule } from 'src/user/user.module';

@Module({
  imports:[
    ConfigModule,
    CommonModule,
    PassportModule.register({defaultStrategy: 'jwt'}),
    
    JwtModule.registerAsync({
      global: true,
      imports: [ConfigModule],
      inject: [ConfigService],
      useFactory: (configService:ConfigService) => {
        return{
          secret: configService.get('JWT_SECRET'),
          signOptions:{
            expiresIn: '1h',
          }
        }
      }
    }),
    forwardRef(() => UserModule)
  ],
  controllers: [AuthController],
  providers: [AuthService, JwtStrategy],
  exports: [ JwtModule, JwtStrategy, PassportModule]
})
export class AuthModule {}
