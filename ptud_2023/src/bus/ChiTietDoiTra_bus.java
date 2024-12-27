/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import Interface.ChiTietDoiTra_Interface;
import dao.ChiTietDoiTra_dao;
import entity.ChiTietDoiTraEntity;
import java.util.ArrayList;

/**
 *
 * @author ploc2
 */
public class ChiTietDoiTra_bus implements ChiTietDoiTra_Interface{
    private ChiTietDoiTra_dao ctdt_dao = new ChiTietDoiTra_dao();

    @Override
    public boolean themChiTietDoiTra(ChiTietDoiTraEntity ctdt) {
        return ctdt_dao.themChiTietDoiTra(ctdt);
    }

    @Override
    public ArrayList<ChiTietDoiTraEntity> getAllCTDTTheoMaDT(String ma) {
        return ctdt_dao.getAllCTDTTheoMaDT(ma);
    }
}
