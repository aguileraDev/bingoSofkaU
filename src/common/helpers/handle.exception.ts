/* eslint-disable prettier/prettier */
import { BadRequestException,  Injectable,  InternalServerErrorException, Logger, NotFoundException, UnauthorizedException } from "@nestjs/common";

@Injectable()
export class HandleException {
    private readonly logger = new Logger();

    constructor(
    ){}

    checkCommonDatabaseExceptions(error:any) :never {  

      if(error.code === '23505') throw new BadRequestException(error.detail)
  
      if(error.code === '337') throw new BadRequestException(`Null field error ${error.detail}`);

      if(error.status === 404) throw new NotFoundException(`Invalid credentials`);
      
      if(error.status === 401) throw new UnauthorizedException(`Invalid credentials`);

      if(error.code === '42601')throw new NotFoundException(error.detail);
      
      this.logger.error(error);
      console.log(error);
      throw new InternalServerErrorException(`Unexpected error check logs`);
    }

}