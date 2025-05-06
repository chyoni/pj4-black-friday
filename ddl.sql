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

create table member_address
(
    member_address_id bigint       not null auto_increment primary key,
    member_id         bigint       not null comment '멤버 ID',
    address           varchar(255) not null comment '멤버 주소',
    alias             varchar(50)  not null comment '별칭'
);
create index idx_member_id on member_address (member_id);

create table delivery
(
    delivery_id     bigint       not null auto_increment primary key,
    order_id        bigint       not null comment '주문 ID',
    product_name    varchar(255) not null comment '주문 상품명',
    product_count   integer      not null comment '주문 상품 개수',
    address         varchar(255) not null comment '배송지',
    delivery_status varchar(50)  not null comment '배송 상태',
    delivery_vendor varchar(50)  not null comment '배송 대행업체',
    reference_code  integer      not null unique comment '배송 대행업체 응답 코드'
);
create index idx_order_id on delivery (order_id);
create index idx_delivery_status on delivery (delivery_status);

create table seller_product
(
    product_id bigint not null auto_increment primary key,
    seller_id  bigint not null comment '판매자 ID'
);

create table product_order
(
    product_order_id bigint      not null auto_increment primary key,
    member_id        bigint      not null comment '주문자 ID',
    product_id       bigint      not null comment '주문 상품 ID',
    count            integer     not null comment '주문 수량',
    status           varchar(50) not null comment '주문 상태',
    payment_id       bigint comment '결제 ID',
    delivery_id      bigint comment '배송 ID'
);
