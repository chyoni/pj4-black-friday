create table member
(
    member_id bigint       not null auto_increment primary key,
    login_id  varchar(255) not null unique comment '로그인 아이디',
    username  varchar(255) not null comment '유저이름'
);

create table payment
(
    payment_id      bigint       not null auto_increment primary key,
    member_id       bigint       not null comment '결제한 멤버 ID',
    order_id        bigint       not null unique comment '주문 ID',
    amount_krw      integer      not null comment '결제 가격',
    method_type     varchar(50)  not null comment '결제 수단',
    payment_payload varchar(500) not null comment '결제 정보',
    payment_status  varchar(50)  not null comment '결제 상태',
    reference_code  integer      not null unique comment '결제 시스템 응답 코드'
);
create index idx_member_id on payment (member_id);

create table payment_method
(
    method_id          bigint       not null auto_increment primary key,
    member_id          bigint       not null comment '멤버 ID',
    method_type        varchar(50)  not null comment '결제 수단',
    credit_card_number varchar(100) not null comment '신용 카드 번호'
);
create index idx_member_id on payment_method (member_id);
