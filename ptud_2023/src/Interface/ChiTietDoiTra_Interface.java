/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import entity.ChiTietDoiTraEntity;
import java.util.ArrayList;

/**
 *
 * @author ploc2
 */
public interface ChiTietDoiTra_Interface {
    public boolean themChiTietDoiTra(ChiTietDoiTraEntity ctdt);
    public ArrayList<ChiTietDoiTraEntity> getAllCTDTTheoMaDT(String ma);
}
