package com.playdata.dao;

import com.playdata.dto.BoardDto;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

    /**
     * board 테이블에서 모든 데이터를 조회
     * @param req
     * @return List<BoardDto>
     */
    public List<BoardDto> selectAll(HttpServletRequest req) {
        Connection conn = (Connection) req.getServletContext().getAttribute("conn");
        List<BoardDto> boardList = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM board ORDER BY id DESC");
            while(rs.next()) {
                BoardDto boardDto = new BoardDto(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("author"),
                        rs.getString("created_at")
                );
                boardList.add(boardDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardList;
    }

    /**
     * board 테이블에서 id에 해당하는 데이터를 조회
     * @param id
     * @param req
     * @return BoardDto
     */
    public BoardDto selectById(String id, HttpServletRequest req) {
        Connection conn = (Connection) req.getServletContext().getAttribute("conn");
        BoardDto boardDto = null;
        String sql = "SELECT * FROM board WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                boardDto = BoardDto.builder()
                        .title(rs.getString("title"))
                        .id(rs.getInt("id"))
                        .content(rs.getString("content"))
                        .created_at(rs.getString("created_at"))
                        .author(rs.getString("author"))
                        .build();
                ;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return boardDto;
    }
}
