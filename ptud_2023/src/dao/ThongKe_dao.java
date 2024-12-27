package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import connectDB.ConnectDB;

public class ThongKe_dao {
	private Connection con;

	public ThongKe_dao() {
		con = ConnectDB.getInstance().getConnection();
	}

	public ArrayList<Object[]> thongKeVeTheoNhanVien(String nam) {
		ArrayList<Object[]> list = new ArrayList<>();
		String sql = "SELECT\r\n"
				+ "				    nv.maNV,\r\n"
				+ "				    nv.ten,\r\n"
				+ "				    COUNT(cd.maVe) AS soLuongVeDaBan\r\n"
				+ "				FROM\r\n"
				+ "				    nhan_vien nv\r\n"
				+ "				LEFT JOIN\r\n"
				+ "				    don_hang dh ON nv.maNV = dh.maNV\r\n"
				+ "				    AND YEAR(dh.ngayTao) = ?\r\n"
				+ "				    AND dh.trangThai = 1\r\n"
				+ "				LEFT JOIN\r\n"
				+ "				    chi_tiet_don_hang cd ON dh.maDH = cd.maDH\r\n"
				+ "				GROUP BY\r\n"
				+ "				    nv.maNV, nv.ten\r\n"
				+ "				ORDER BY\r\n"
				+ "				    soLuongVeDaBan DESC";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, Integer.parseInt(nam));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Object[] row = new Object[] { rs.getString("maNV"), rs.getString("ten"), rs.getInt("soLuongVeDaBan") };
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<Object[]> thongKeNhanVien() {
		ArrayList<Object[]> dsNhanVien = new ArrayList<>();
		String sql = "SELECT "
				+ "    CASE WHEN trangThai = 1 THEN N'Đang làm việc' ELSE N'Đã nghỉ việc' END as TrangThai, "
				+ "    COUNT(*) as SoLuong " + "FROM nhan_vien " + "GROUP BY trangThai;";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Object[] row = { rs.getString("TrangThai"), rs.getInt("SoLuong") };
				dsNhanVien.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsNhanVien;
	}

	public ArrayList<Object[]> thongKeVeTheoTau(String nam) {
		ArrayList<Object[]> list = new ArrayList<>();
		String sql = "SELECT\r\n"
				+ "    t.maTau,\r\n"
				+ "    t.ten,\r\n"
				+ "    COALESCE(COUNT(ve.maVe), 0) AS soLuongVeDaBan\r\n"
				+ "FROM \r\n"
				+ "    tau t\r\n"
				+ "LEFT JOIN \r\n"
				+ "    hanh_trinh ht ON t.maTau = ht.maTau\r\n"
				+ "LEFT JOIN \r\n"
				+ "    ve ve ON ht.maHT = ve.maHT\r\n"
				+ "LEFT JOIN \r\n"
				+ "    chi_tiet_don_hang ctdh ON ve.maVe = ctdh.maVe\r\n"
				+ "LEFT JOIN \r\n"
				+ "    don_hang dh ON ctdh.maDH = dh.maDH\r\n"
				+ "    AND YEAR(dh.ngayTao) = ?\r\n"
				+ "    AND dh.trangThai = 1\r\n"
				+ "GROUP BY \r\n"
				+ "    t.maTau, t.ten\r\n"
				+ "ORDER BY \r\n"
				+ "    soLuongVeDaBan DESC";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, Integer.parseInt(nam));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Object[] row = new Object[] { rs.getString("maTau"), rs.getString("ten"), rs.getInt("soLuongVeDaBan") };
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<Object[]> thongKeTop5NhanVien(String thang, String nam) {
		ArrayList<Object[]> list = new ArrayList<>();
		String sql = "\r\n"
				+ "SELECT TOP 5\r\n"
				+ "				        nv.maNV,\r\n"
				+ "				        nv.ten,\r\n"
				+ "				        COALESCE(COUNT(ctdh.maVe), 0) as soLuongVeDaBan\r\n"
				+ "				    FROM nhan_vien nv\r\n"
				+ "				    LEFT JOIN don_hang dh ON nv.maNV = dh.maNV\r\n"
				+ "				    LEFT JOIN chi_tiet_don_hang ctdh ON dh.maDH = ctdh.maDH\r\n"
				+ "				    WHERE dh.trangThai = 1\r\n"
				+ "				    AND YEAR(dh.ngayTao) = 2024\r\n"
				+ "				    GROUP BY nv.maNV, nv.ten\r\n"
				+ "				    ORDER BY soLuongVeDaBan DESC";


		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Object[] row = new Object[] { rs.getString("maNV"), rs.getString("ten"), rs.getInt("soLuongVeDaBan") };
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int thongKeTongVeDaBan(String nam) {
		int tongVe = 0;
		String sql = """
				    SELECT COUNT(ctdh.maVe) as TongVe
				    FROM DonHang dh
				    JOIN ChiTietDonHang ctdh ON dh.maDH = ctdh.maDH
				    WHERE YEAR(dh.ngayTao) = ? AND dh.trangThai = 1
				""";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, Integer.parseInt(nam));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				tongVe = rs.getInt("TongVe");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tongVe;
	}
}