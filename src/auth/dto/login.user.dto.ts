/* eslint-disable prettier/prettier */
import { IsEmail, IsNotEmpty,  Matches, MaxLength, MinLength } from 'class-validator';

export class LoginUserDto {
  @IsNotEmpty()
  @IsEmail()
  email: string;

  @MinLength(6)
  @MaxLength(16)
  @Matches(/(?:(?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/, {
    message:
      'The password must have a Uppercase, lowercase letter and a number',
  })
  password: string;
}
