/* eslint-disable prettier/prettier */
import { Controller, Get, Body, Patch, Param, Query, UseGuards } from '@nestjs/common';
import { UsersService } from './user.service';
import { UpdateUserDto } from './dto/update-user.dto';
import { PaginationDto } from 'src/common/dto/pagination.dto';
import { AuthGuard } from '@nestjs/passport';

@UseGuards(AuthGuard('jwt'))
@Controller('user')
export class UserController {
  constructor(private readonly userService: UsersService) { }


  @Get()
  findAll(
    @Query() paginationDto: PaginationDto) {
    return this.userService.findAll(paginationDto);
  }

  @Get('active')
  findAllActive(
    @Query() paginationDto: PaginationDto) {
    return this.userService.findAllActive(paginationDto);
  }

  @Get(':term')

  findOne(@Param ('term') term: string) {
    return this.userService.findOneByTerm(term);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateUserDto: UpdateUserDto) {
    return this.userService.update(id, updateUserDto);
  }

  validateJwt(id: string) {
    return this.userService.findOneForJwtValidate(id);
  }
}
