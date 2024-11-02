/* eslint-disable prettier/prettier */
import { Module } from '@nestjs/common';
import { HandleException } from './helpers/handle.exception';

@Module({
    exports: [HandleException],
    controllers: [],
    providers: [HandleException],
})
export class CommonModule {}
