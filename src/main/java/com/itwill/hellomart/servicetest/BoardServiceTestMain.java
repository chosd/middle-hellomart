package com.itwill.hellomart.servicetest;

import java.util.List;

import com.itwill.hellomart.board.Board;
import com.itwill.hellomart.board.BoardService;

public class BoardServiceTestMain {

	public static void main(String[] args) throws Exception {
		List<Board> BoardList =BoardService.getInstance().findBoardList(1);
		System.out.println(BoardList);
	}

}
