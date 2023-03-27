package com.dz.board.dao;

import com.dz.board.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BoardDAO {
    List<BoardVO> list();

    BoardVO findById(String id);

    boolean insert(BoardVO board);

    boolean save(BoardVO board);

    boolean delete(String id);
}
