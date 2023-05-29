--guard1 멤버한사람의  카트에 제품번호 존재여부
select count(*)  as p_count from cart c join userinfo u on c.userid=u.userid where u.userid='guard1' and c.p_no=1;

--guard1 멤버한사람의 카트아이템리스트
select * 
from cart c join userinfo u
on c.userid=u.userid 
join product p 
on p.p_no=c.p_no
where u.userid='guard1';

--카트아이템 1개의 제품정보(guard1 멤버한사람)
select * from cart c join product p on c.p_no=p.p_no where cart_no=1 ;

--guard1 카트에 있는 1번제품의 수량증가
update cart set cart_qty=cart_qty+1 where userid='guard1' and p_no=1;
--guard1 카트에 있는 cart_no 1번의 수량3개 수정
update cart set cart_qty=3 where cart_no=1;

--guard1님 카트아이템1개삭제
delete from cart where cart_no=1;
--guard1님 카트아이템모두삭제
delete from cart where userid='guard1';


--1. 멤버 한사람의 주문전체목록
select * from orders where userid='guard1';

--2. 주문한개(멤버 한사람의) 
select * from orders where o_no=1;


--3. 주문한개의  주문상세 여러개(주문상세)
select * from order_item where o_no = 1;

--4. 주문한개의 주문상세,제품정보 여러개(주문상세,제품)
select * from orders o join order_item oi on o.o_no=oi.o_no  join  product p on oi.p_no=p.p_no 
where o.userid='guard1' and o.o_no = 1;

--5. 주문한개삭제(주문1개삭제,주문상세삭제)
--on delete cascade
delete from order_item where o_no=1;
delete from orders where o_no=1;
--rollback;
--6. 멤버한사람의 주문내역전체삭제
----on delete cascade
--select j_no from orders where userid='guard1';
delete from order_item where o_no in(select o_no from orders where userid='guard1');
delete from orders where userid='guard1';

--7. 멤버 한 사람의 주문 건당 주문 주소 확인
select*from orders o join address a on o.o_no=a.o_no where o.userid='guard1';

/*****************board select******************/
select * from board where p_no=6;
-- 상품넘버(PK)로 전체 게시글 조회
SELECT s.* FROM( 
            SELECT boardno, title, regdate, readcount,groupno, step, depth, userid, p_no
            FROM board 
            ORDER BY groupno DESC, step ASC ) s 
where s.p_no = 1;

-- 상품넘버(PK)로 페이징 게시글 조회
SELECT * FROM ( 
            SELECT rownum idx, s.* FROM( 
                            SELECT boardno, title, regdate, readcount,groupno, step, depth, userid, p_no
                            FROM board 
                            ORDER BY groupno DESC, step ASC ) s 
                            where s.p_no = 2) 
WHERE idx >= 1 AND idx <= 100;

-- 유저(userid)전체 게시글 조회
SELECT * FROM ( 
            SELECT rownum idx, s.* FROM( 
                            SELECT boardno, title, regdate, readcount,groupno, step, depth, userid, p_no
                            FROM board 
                            ORDER BY groupno DESC, step ASC ) s 
                            where s.userid = 'csd') 
WHERE idx >= 1 AND idx <= 11;

SELECT * FROM ( SELECT rownum idx, s.* FROM( SELECT boardno, title, regdate, readcount,groupno, step, depth, userid, p_no FROM board ORDER BY groupno DESC, step ASC ) s  where s.userid = 'csd')  WHERE idx >= 1 AND idx <= 100;

--답글쓰기
/*현재글의 데이타를 기반으로 작업한다.
    1. boardno : 시퀀스증가
    2. groupno : 현재글의 groupno
    3. step    : 현재글의 step  + 1
    4. depth   : 현재글의 depth + 1
 */
-- update 현재글과 같은그룹번호들중에서현재글의 step보다큰 step을가진 게시물들의 step을 1씩 증가시킨다.
update board set step=step+1 where step > 1 and groupno=385;
-- insert
insert into board(boardno,title,content,groupno,step,depth,userid,p_no) 
        values(board_boardno_SEQ.nextval,
            '답변게시글'||board_boardno_SEQ.currval,
            '이네요'||board_boardno_SEQ.currval,
            385,
            2,
            1,
            'guard1',
            10
            );
--상품게시판 답변게시물 존재여부확인
SELECT count(*) cnt FROM board 
WHERE groupno = 385 AND depth >= 1 AND step >= 2 
ORDER BY step,depth ASC;

--게시글 삭제
DELETE board WHERE boardno = 380;

--게시글 업데이트
UPDATE board SET title = 'ㄳㄱ수정', content = '내용수정'  WHERE userid = 'csd' and boardno = 384;

--게시글 1개 조회(board넘버)
SELECT 
boardno, title, content, 
regdate, readcount,
groupno, step, depth, userid, p_no 
FROM board 
WHERE boardno = 384;

--게시글 여러개 조회(userid)
SELECT 
boardno, title, content, 
regdate, readcount,
groupno, step, depth, userid, p_no 
FROM board 
WHERE userid = 'guard1';

--상품게시글 총 건수 조회
SELECT COUNT(*) FROM board where p_no=6;

/**********테스트****************/
--유저게시글 총 건수 조회
SELECT COUNT(*) FROM board where userid='guard1';
select count(*)  as p_count from cart c join userinfo u on c.userid=u.userid where u.userid='guard1' and c.p_no=1;

SELECT COUNT(*) FROM product;
select * from product where p_no>=3 and p_no<=6;

SELECT COUNT(*) FROM product where p_name like '%'||'삼성'||'%';
--상품 검색후 페이징용 리스트
SELECT * FROM ( 
            SELECT rownum idx, p.* FROM( 
                            SELECT p_no,p_name,p_price,p_image,p_desc,ct_no
                            FROM product
                            where p_name like '%'||'삼성'||'%' )p
                            )
WHERE idx >= 2 AND idx <= 5;

SELECT * FROM ( SELECT rownum idx, p.* FROM( SELECT p_no,p_name,p_price,p_image,p_desc,ct_no FROM product where p_name like '%'||'삼성'||'%' )p) WHERE idx >= 2 AND idx <= 5;


rollback;














