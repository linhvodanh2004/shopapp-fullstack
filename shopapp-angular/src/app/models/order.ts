export interface Order {
    user_id: number;
    fullname: string;
    email: string;
    phone_number: string;
    address: string;
    note: string;
    total_money: number;
    shipping_date: Date;
    shipping_method: string;
    shipping_address: string;
    payment_method: string;
}