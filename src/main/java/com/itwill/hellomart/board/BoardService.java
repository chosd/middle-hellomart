package com.itwill.hellomart.board;

import java.util.List;

import com.itwill.hellomart.board.util.PageMaker;


public class BoardService {
	private static BoardService _instance;
	private BoardDao boardDao;
	
	private BoardService() throws Exception {
		boardDao=new BoardDao();
	}
	
	//한번만 생성되도록 자기참조 멤버변수 사용 - 싱글톤기법
	public static BoardService getInstance() throws Exception{
		if(_instance==null) {
			_instance=new BoardService();
		}
		return _instance;
	}
	/*
	 * 게시물삭제
	 */
	public int remove(int boardno) throws Exception,BoardException{
		Board tempBoard = boardDao.findBoard(boardno);
		boolean rExist = boardDao.countReplay(tempBoard);
		if (rExist) {
			//답글존재
			throw new BoardException("답글이 존재하는 게시글 삭제 불가");
		}else {
			return boardDao.remove(tempBoard.getBoardno());
		}
	}
	/*
	 * 상품게시물생성
	 */
	public int create(Board board, String sUserId, int p_no)throws Exception{
		return  boardDao.create(board, sUserId, p_no);
	}
	/*
	 * 답글쓰기
	 */
	public int createReplay(Board board, String sUserId) throws Exception{
		return boardDao.createReply(board, sUserId);
	}
	/*
	 * 게시물 1개
	 */
	public Board findBoard(int boardNo)throws Exception{
		Board board=boardDao.findBoard(boardNo);
		return board;
	}
	public void updateHitCount(int boardNo) throws Exception{
		boardDao.increaseReadCount(boardNo);
	}
	/*
	 * 상품게시물리스트 페이징
	 */
	public BoardListPageMakerDto findBoardList(int currentPage, int p_no) throws Exception{
		//1.전체글의 갯수
		int totalRecordCount = boardDao.getBoardCount(p_no);
		
		//2.paging계산(PageMaker 유틸클래스)
		PageMaker pageMaker=new PageMaker(totalRecordCount,currentPage);
	
		//3.게시물데이타 얻기
		List<Board> boardList=
				boardDao.findBoardList(pageMaker.getPageBegin(),
											pageMaker.getPageEnd(), p_no);
		
		BoardListPageMakerDto pageMakerBoardList=new BoardListPageMakerDto();
		pageMakerBoardList.itemList=boardList;
		pageMakerBoardList.pageMaker=pageMaker;
		return pageMakerBoardList;
	}
	/*
	 * 상품게시물전체리스트
	 */
	public List<Board> findBoardList(int p_no) throws Exception{
		return boardDao.findBoardList(p_no);
	}
	/*
	 * 유저게시물리스트
	 */
	public BoardListPageMakerDto findBoardListByUser(int currentPage, String sUserId) throws Exception{
		//1.전체글의 갯수
		int totalRecordCount = boardDao.getBoardCountByUserId(sUserId);
		
		//2.paging계산(PageMaker 유틸클래스)
		PageMaker pageMaker=new PageMaker(totalRecordCount,currentPage);
	
		//3.게시물데이타 얻기
		List<Board> boardList=
				boardDao.findBoardByUserId(pageMaker.getPageBegin(),
											pageMaker.getPageEnd(), sUserId);
		
		BoardListPageMakerDto pageMakerBoardList=new BoardListPageMakerDto();
		pageMakerBoardList.itemList=boardList;
		pageMakerBoardList.pageMaker=pageMaker;
		return pageMakerBoardList;
	}
	/*
	 * 게시물수정
	 */
	public int update(Board board) throws Exception {
		return boardDao.update(board);
	}
	
}
