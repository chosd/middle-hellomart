package com.itwill.hellomart.board;

public class BoardSQL {
	//-- 상품게시판 새글쓰기
	public static final String BOARD_INSERT=
			"insert into board(boardno, title, content, groupno, step, userid, p_no) values(board_boardno_SEQ.nextval,?,?,board_boardno_SEQ.currval,1,?,?)";
	
	//--상품게시판 답글쓰기
	//-- update 현재글과 같은그룹번호들중에서현재글의 step보다큰 step을가진 게시물들의 step을 1씩 증가시킨다.
	public static final String BOARD_UPDATE_REPLY=
			"update board set step = step + 1 where step > ? and groupno = ? and p_no=?";
	//-- insert 후기글 삽입
	public static final String BOARD_INSERT_REPLY=
			"insert into board(boardno, title, content, groupno, step, depth, userid, p_no) values(board_boardno_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?)";
	
	/*
	 select * from(
	 				select rownum idx,
	 					   sorted_board.* from(
	 					   						select boardno, title, 
	 											       writer,regdate, 
	 												   readcount,groupno, 
	 												   step, depth 
	 					  						 	   from board 
	 					  						 	   order by groupno desc,step asc
	 					  					  ) sorted_board
	 			   ) rownum_board
	 where idx >= ? and idx <= ?
	 */
	public static final String BOARD_SELECT_PAGE=
			"SELECT * FROM ( SELECT rownum idx, s.* FROM( SELECT boardno, title, regdate, readcount,groupno, step, depth, userid FROM board ORDER BY groupno DESC, step ASC ) s ) WHERE idx >= ? AND idx <= ? and p_no=?";
	public static final String BOARD_SELECT_ALL_BY_P_NO=
			"SELECT s.* FROM(SELECT boardno, title, regdate, readcount,groupno, step, depth, userid, p_no FROM board ORDER BY groupno DESC, step ASC ) s where s.p_no = ?";
	public static final String BOARD_SELECT_ALL_BY_USERID=
			"SELECT * FROM ( SELECT rownum idx, s.* FROM( SELECT boardno, title, regdate, readcount,groupno, step, depth, userid, p_no FROM board ORDER BY groupno DESC, step ASC ) s  where s.userid = ?)  WHERE idx >= ? AND idx <= ?";
}
