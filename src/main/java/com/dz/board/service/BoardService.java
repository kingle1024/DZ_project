package com.dz.board.service;

import com.dz.board.vo.BoardVO;
import java.util.List;

public interface BoardService {
    List<BoardVO> list();

    BoardVO findById(String id);

    boolean insert(BoardVO board);

    boolean save(BoardVO board);

    boolean delete(String id);
}
