/* eslint-disable prettier/prettier */
/* eslint-disable @typescript-eslint/no-unused-vars */
import { BadRequestException, Injectable, Logger, NotFoundException, UnauthorizedException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { DataSource, Repository } from 'typeorm';
import { User } from './entities/user.entity';

import * as bcrypt from 'bcrypt';

import { CreateUserDto, UpdateUserDto } from './dto';
import { validate as isUUID } from 'uuid';

import { JwtPayload } from 'src/auth/interfaces/jwt.payload.interface';
import { JwtService } from '@nestjs/jwt';
import { HandleException } from 'src/common/helpers/handle.exception';
import { PaginationDto } from 'src/common/dto/pagination.dto';

@Injectable()
export class UsersService {

  private readonly logger = new Logger();

  constructor(
    @InjectRepository(User)
    private readonly userRepository: Repository<User>,

    private readonly dataSource: DataSource,

    private readonly handleException: HandleException,

    private readonly jwtService: JwtService,
  ) { }

  async create(createUserDto: CreateUserDto) {
    const { password, ...rest } = createUserDto;

    try {

      const user = this.userRepository.create({
        ...rest,
        password: bcrypt.hashSync(password, 11),
      })
      
      const savedUser = await this.userRepository.save(user);

      const{password: _, ...userData} = savedUser;

      return {
        ...userData,
        token: this.generateJwtToken({ id: savedUser.id })
      };

    } catch (error) {
      this.handleException.checkCommonDatabaseExceptions(error);
    }
  }

  async findAll(paginationDto: PaginationDto) {
    const { limit = 4, offset = 0 } = paginationDto;

    try {

      const users = await this.userRepository.find({
        take: limit,
        skip: offset,
        order: {
          name: 'ASC'
        }
      });

      return users;

    } catch (error) {
      this.handleException.checkCommonDatabaseExceptions(error)
    }
  }

  async findAllActive(paginationDto: PaginationDto) {
    const { limit = 10, offset = 0 } = paginationDto;
    try {
      const users = await this.userRepository.find({
        take: limit,
        skip: offset,
        where: {
          isActive: true,
        },
        order: {
          name: 'ASC'
        }
      })
      return users;
    } catch (error) {
      this.handleException.checkCommonDatabaseExceptions(error)
    }
  }


  async findOneByTerm(term: string) {
    let user: User;

    if (!isUUID(term)) {
      const queryBuilder = this.userRepository.createQueryBuilder();
      try {
        user = await queryBuilder
          .where('email=:email or name=:name or lastname=:lastname', {
            email: term,
            name: term,
            lastname: term,
          })
          .getOne();
      } catch (error) {
        this.handleException.checkCommonDatabaseExceptions(error)
      }
    }

    if (isUUID(term)) user = await this.userRepository.findOneBy({ id: term });


    if (!user) throw new NotFoundException(`User with term ${term} not exist`);

    return user;
  }


  async findOneUserForLogin(email: string) {

    const user = await this.userRepository.findOne({
      where: {
        email
      },
      select: { name: true, email: true, password: true, id: true, isActive: true }
    });

    if (!user) throw new UnauthorizedException(`invalid credentials 1`);

    if (!user.isActive) throw new UnauthorizedException(`Invalid credentials 2`);

    return {
      ...user,
      token: this.generateJwtToken({ id: user.id }),
    }
  }

  async findOneForJwtValidate(id: string): Promise<User> {

    try {
      const user = await this.userRepository.findOneBy({ id });

      if (!user) throw new UnauthorizedException(`Invalid credentials`);

      if (!user.isActive) throw new UnauthorizedException(`invalid jwt`);

      return user;

    } catch (error) {
      this.handleException.checkCommonDatabaseExceptions(error);

    }

  }

  async update(id: string, updateUserDto: UpdateUserDto) {

    const { ...toUpdate } = updateUserDto;

    const user = await this.userRepository.preload({ id, ...toUpdate });

    if (!user) throw new BadRequestException(`User with id ${id} not valid`);

    const queryRunner = this.dataSource.createQueryRunner();
    queryRunner.connect();
    queryRunner.startTransaction();

    try {

      await queryRunner.manager.save(user);
      await queryRunner.commitTransaction();
      await queryRunner.release();

      return this.findOneByTerm(id);

    } catch (error) {
      await queryRunner.rollbackTransaction();
      await queryRunner.release();
      this.handleException.checkCommonDatabaseExceptions(error);

    }

  }

  generateJwtToken(payload: JwtPayload) {
    return this.jwtService.sign(payload);
  }

}
