/* eslint-disable prettier/prettier */
import { Entity, PrimaryGeneratedColumn, Column, BeforeInsert, Index} from "typeorm";

@Entity({name: 'players'})
export class User {

    @PrimaryGeneratedColumn('uuid')
    id:string;

    @Column('text',{
        nullable:false,
    })
    name:string;

    @Column('text',{
        nullable:false,
    })
    lastname: string;
   
    @Column('text',{
        unique: true,
        nullable:false,
    })
    @Index()
    email: string;

    @Column('text',{
        select:false,
    })
    password: string;

    @Column('bool',{
        default: true,
    })
    isActive: boolean;

    @Column('text',{
        default: () => 'CURRENT_TIMESTAMP',
    })
    registerDate: string;
    
    @BeforeInsert()
    normalizeFields?():void{
       this.name = this.name.toLowerCase();
       this.lastname = this.lastname.toLowerCase();
    }

    setDateNow() {
        const dateNow = new Date();
        const dateFormat = new Intl.DateTimeFormat('es-CO', {
          timeStyle: 'long',
          timeZone: 'America/Bogota',
          dateStyle: 'medium',
        }).format(dateNow);
    
        this.registerDate = dateFormat;
    }
}