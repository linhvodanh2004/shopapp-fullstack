import { IsString, IsNotEmpty} from 'class-validator';

export class LoginDto {
    @IsString()
    @IsNotEmpty()
    phone_number: string;

    @IsString()
    @IsNotEmpty()
    password: string;

    constructor(data: any) {
        this.phone_number = data.phone_number;
        this.password = data.password;
    }
}