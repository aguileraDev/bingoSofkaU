/* eslint-disable prettier/prettier */
import { IsString, MinLength, IsEmail, IsNotEmpty, MaxLength, Matches } from "class-validator";

export class CreateUserDto {

  @IsString()
  @MinLength(3)
  name: string;

  @IsString()
  @MinLength(3)
  lastname: string;

  @IsNotEmpty()
  @IsEmail()
  @MinLength(3)
  email: string;

  @IsString()
  @MinLength(6)
  @MaxLength(16)
  @Matches(/(?:(?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/, {
    message:
      'The password must have a Uppercase, lowercase letter, number and max length of 16',
  })
  password: string;

}