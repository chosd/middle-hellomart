package com.itwill.hellomart.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.itwill.hellomart.common.DataSourceFactory;


public class BoardDao {
	private DataSource dataSource;
	
	public BoardDao() throws Exception {
		
		//dataSource = DataSourceFactory.getDataSource();
		Properties properties = new Properties();
		properties.load(DataSourceFactory.class.getResourceAsStream("/jdbc.properties"));
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(properties.getProperty("driverClassName"));
		basicDataSource.setUrl(properties.getProperty("url"));
		basicDataSource.setUsername(properties.getProperty("username"));
		basicDataSource.setPassword(properties.getProperty("password"));
		dataSource = basicDataSource;
//		
	}
	/**
	 * 상품게시판에 새로운 게시물을 추가하는 메써드.
	 * @throws Exception 
	 */
	public int create(Board board, String sUserId,int p_no) throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(BoardSQL.BOARD_INSERT);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, sUserId);
			pstmt.setInt(4, p_no);
			int result = pstmt.executeUpdate();
			return result;
		} finally {
			con.close();
		}
		
	}
	/**
	 * 상품게시판에 답글 게시물을 추가하는 메써드
	 */
	public int createReply(Board board, String sUserId) throws Exception{
		Connection con =null;
		PreparedStatement pstmt=null;
		int count=0;
		try {
			// 댓글을 작성할 대상글(원글)의 정보를 조회
			Board temp = this.findBoard(board.getBoardno());
			
			// 영향을 받는 기존 글들의 논리적인 순서 번호 변경
			con= dataSource.getConnection();
			pstmt = con.prepareStatement(BoardSQL.BOARD_UPDATE_REPLY);
			pstmt.setInt(1, temp.getStep());// step 번호
			pstmt.setInt(2, temp.getGroupno());// group 번호
			pstmt.setInt(3, temp.getP_no());
			pstmt.executeUpdate();
			pstmt.close();
			
			//답글 삽입
			pstmt = con.prepareStatement(BoardSQL.BOARD_INSERT_REPLY);
			pstmt.setString(1, board.getTitle());// 제목
			pstmt.setString(2, board.getContent());// 내용
			pstmt.setInt(3, temp.getGroupno());// group no
			pstmt.setInt(4, temp.getStep() + 1);// step
			pstmt.setInt(5, temp.getDepth() + 1);// depth
			pstmt.setString(6, sUserId);// 작성자
			pstmt.setInt(7, temp.getP_no());// 상품 no
			count = pstmt.executeUpdate();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
		return count;
	}
	/**
	 * 상품게시판에 게시물 리스트를 반환(게시물시작번호,게시물끝번호)
	 */
	public ArrayList<Board> findBoardList(int p_no) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// 조회 결과에 접근하는 참조 변수
		// 데이터베이스의 데이터를 읽어서 저장할 객체 컬렉션
		ArrayList<Board> boards = new ArrayList<Board>();
		try {
			con = dataSource.getConnection();
			/*
			SELECT s.* FROM( 
	            SELECT boardno, title, regdate, readcount,groupno, step, depth, userid, p_no
	            FROM board 
	            ORDER BY groupno DESC, step ASC ) s 
			where s.p_no = 1;
			 */

			pstmt = con.prepareStatement(BoardSQL.BOARD_SELECT_ALL_BY_P_NO);
			pstmt.setInt(1, p_no);
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				Board board = new Board();
				board.setBoardno(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setRegdate(rs.getDate(3));
				board.setReadcount(rs.getInt(4));
				board.setGroupno(rs.getInt(5));
				board.setStep(rs.getInt(6));
				board.setDepth(rs.getInt(7));
				board.setUserId(rs.getString(8));
				board.setP_no(rs.getInt(9));

				boards.add(board);
			}
		} finally {
			// 6. 연결닫기
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception ex) {
				}
		}
		return boards;
	}
	/**
	 * 상품게시판에 게시물 리스트를 반환(게시물시작번호,게시물끝번호)
	 */
	public ArrayList<Board> findBoardList(int start, int last, int p_no) throws Exception{
		// System.out.println("" + start + " ~ " + last);
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// 조회 결과에 접근하는 참조 변수
		// 데이터베이스의 데이터를 읽어서 저장할 객체 컬렉션
		ArrayList<Board> boards = new ArrayList<Board>();
		try {
			con = dataSource.getConnection();
			StringBuffer sql = new StringBuffer(500);
			sql.append("SELECT * ");
			sql.append("FROM ");

			sql.append("( ");
			sql.append("	SELECT ");
			sql.append("		rownum idx, s.* ");
			sql.append("	FROM ");

			sql.append("	( ");
			sql.append("		SELECT ");
			sql.append("			boardno, title, ");
			sql.append("			regdate, readcount, ");
			sql.append("			groupno, step, depth, userid, p_no");
			sql.append("		FROM ");
			sql.append("			board ");
			sql.append("		ORDER BY groupno DESC, step ASC ");
			sql.append("	) s ");

			sql.append("where s.p_no = ?) ");

			sql.append("WHERE idx >= ? AND idx <= ?");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, p_no);
			pstmt.setInt(2, start);
			pstmt.setInt(3, last);
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				Board board = new Board();
				board.setBoardno(rs.getInt(2));
				board.setTitle(rs.getString(3));
				board.setRegdate(rs.getDate(4));
				board.setReadcount(rs.getInt(5));
				board.setGroupno(rs.getInt(6));
				board.setStep(rs.getInt(7));
				board.setDepth(rs.getInt(8));
				board.setUserId(rs.getString(9));
				board.setP_no(rs.getInt(10));

				boards.add(board);
			}
		} finally {
			// 6. 연결닫기
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception ex) {
				}
		}
		return boards;
	}
	/**
	 * 유저가 작성한 게시물 전체 조회
	 */
	public ArrayList<Board> findBoardByUserId(int start, int last,String sUserId) throws Exception {
		// System.out.println("" + start + " ~ " + last);
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// 조회 결과에 접근하는 참조 변수
		// 데이터베이스의 데이터를 읽어서 저장할 객체 컬렉션
		ArrayList<Board> boards = new ArrayList<Board>();
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(BoardSQL.BOARD_SELECT_ALL_BY_USERID);
			pstmt.setString(1, sUserId);
			pstmt.setInt(2, start);
			pstmt.setInt(3, last);
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				Board board = new Board();
				board.setBoardno(rs.getInt(2));
				board.setTitle(rs.getString(3));
				board.setRegdate(rs.getDate(4));
				board.setReadcount(rs.getInt(5));
				board.setGroupno(rs.getInt(6));
				board.setStep(rs.getInt(7));
				board.setDepth(rs.getInt(8));
				board.setUserId(rs.getString(9));
				board.setP_no(rs.getInt(10));

				boards.add(board);
			}
		}finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
		return boards;
	}
	/**
	 * 상품게시판에 답변게시물 존재여부확인메쏘드
	 */
	public boolean countReplay(Board board) throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Boolean isExist = false;
		int cnt = 0;
		try {
			con = dataSource.getConnection();
			StringBuffer sql = new StringBuffer(300);
			sql.append("SELECT ");
			sql.append("count(*) cnt ");
			sql.append("FROM board ");
			sql.append("WHERE groupno = ? ");
			sql.append("AND depth >= ? ");
			sql.append("AND step >= ? ");
			sql.append("and p_no = ?");
			sql.append("ORDER BY step,depth ASC");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, board.getGroupno());
			pstmt.setInt(2, board.getDepth());
			pstmt.setInt(3, board.getStep());
			pstmt.setInt(4, board.getP_no());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}
			if (cnt > 1) {
				isExist = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
		return isExist;
	}
	/**
	 * 상품게시판에 게시물을 삭제하는 메써드.
	 */
	public int remove(int boardNo) throws Exception{

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			con = dataSource.getConnection();
			String sql = "DELETE board WHERE boardno = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			count = pstmt.executeUpdate();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
		return count;
	}
	/**
	 * 상품게시판에 게시물내용을 수정하는 메써드.
	 */
	public int update(Board board) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			con = dataSource.getConnection();
			//String sql = "UPDATE board " + "SET title = ?, content = ? ,writer = ?" + "WHERE boardno = ?";
			String sql = "UPDATE board SET title = ?, content = ? where boardno = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getBoardno());
			count = pstmt.executeUpdate();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();;
			} catch (Exception ex) {
			}
		}
		return count;
	}
	/**
	 * 상품게시판에 게시물 번호에 해당하는 게시물 정보를 반환하는 메써드.
	 */
	public Board findBoard(int boardNo) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board board = null;
		try {
			con = dataSource.getConnection();
			StringBuffer sql = new StringBuffer(300);
			sql.append("SELECT ");
			sql.append("boardno, title, content, ");
			sql.append("regdate, readcount, ");
			sql.append("groupno, step, depth, userid, p_no ");
			sql.append("FROM board ");
			sql.append("WHERE boardno = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				board = new Board();
				board.setBoardno(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setContent(rs.getString(3));
				board.setRegdate(rs.getDate(4));
				board.setReadcount(rs.getInt(5));
				board.setGroupno(rs.getInt(6));
				board.setStep(rs.getInt(7));
				board.setDepth(rs.getInt(8));
				board.setUserId(rs.getString(9));
				board.setP_no(rs.getInt(10));
			}
		}finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
		return board;
	}
	/**
	 * 상품게시판에 게시물 조회수를 1 증가.
	 */
	public void increaseReadCount(int number) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE board " + "SET readcount = readcount + 1 " + "WHERE boardno = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
	}
	/**
	 * 상품게시판에 게시물 총 건수를 조회, 반환
	 */
	public int getBoardCount(int p_no) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(*) FROM board where p_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, p_no);
			rs = pstmt.executeQuery();
			if (rs.next())
				count = rs.getInt(1);

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
		return count;
	}
	/**
	 * 유저 게시물 총 건수를 조회, 반환
	 */
	public int getBoardCountByUserId(String sUserId) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(*) FROM board where userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sUserId);
			rs = pstmt.executeQuery();
			if (rs.next())
				count = rs.getInt(1);

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
		return count;
	}
}
