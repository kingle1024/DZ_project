package com.dz.board.service;

import com.dz.board.dao.BoardDAO;
import com.dz.board.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
    private final BoardDAO boardDAO;

    @Autowired
    public BoardServiceImpl(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    @Override
    public List<BoardVO> list() {
        return boardDAO.list();
    }

    @Override
    public BoardVO findById(String id) {
        return boardDAO.findById(id);
    }

    @Override
    public boolean insert(BoardVO board) {
        return boardDAO.insert(board);
    }

    @Override
    public boolean save(BoardVO board) {
        return boardDAO.save(board);
    }

    @Override
    public boolean delete(String id) {
        return boardDAO.delete(id);
    }
}
