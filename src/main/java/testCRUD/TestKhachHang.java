package testCRUD;

import dao.KhachHang_dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import entity.KhachHang;

import java.util.List;
import java.util.Scanner;

public class TestKhachHang {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
        KhachHang_dao kh_dao = new KhachHang_dao(em);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU KHÁCH HÀNG =====");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Cập nhật khách hàng");
            System.out.println("3. Xóa khách hàng");
            System.out.println("4. Hiển thị tất cả khách hàng");
            System.out.println("5. Tìm khách hàng theo tên");
            System.out.println("6. Tìm khách hàng theo mã");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    KhachHang newkh = new KhachHang();
                    System.out.print("Nhập mã khách hàng: ");
                    newkh.setIdKH(scanner.nextLine());

                    System.out.print("Nhập tên khách hàng: ");
                    newkh.setHoTen(scanner.nextLine());

                    System.out.print("Nhập số điện thoại: ");
                    newkh.setSdt(scanner.nextLine());

                    System.out.print("Nhập Hạng Mức: ");
                    newkh.setHangMuc(scanner.nextLine());


                    if (kh_dao.save(newkh)) {
                        System.out.println("Thêm khách hàng thành công.");
                    } else {
                        System.out.println("Thêm khách hàng thất bại.");
                    }
                    break;

                case 2:
                    System.out.print("Nhập mã khách hàng cần cập nhật: ");
                    String maKHUpdate = scanner.nextLine();
                    KhachHang khachHangUpdate = kh_dao.findById(maKHUpdate);
                    if (khachHangUpdate != null) {
                        System.out.print("Nhập tên mới: ");
                        khachHangUpdate.setHoTen(scanner.nextLine());

                        System.out.print("Nhập số điện thoại mới: ");
                        khachHangUpdate.setSdt(scanner.nextLine());

                        System.out.print("Nhập hạng mức mới: ");
                        khachHangUpdate.setHangMuc(scanner.nextLine());

                        if (kh_dao.update(khachHangUpdate)) {
                            System.out.println("Cập nhật khách hàng thành công.");
                        } else {
                            System.out.println("Cập nhật khách hàng thất bại.");
                        }
                    } else {
                        System.out.println("Không tìm thấy khách hàng với mã " + maKHUpdate);
                    }
                    break;

                case 3:
                    System.out.print("Nhập mã khách hàng cần xóa: ");
                    String maKHDelete = scanner.nextLine();
                    if (kh_dao.delete(maKHDelete)) {
                        System.out.println("Xóa khách hàng thành công.");
                    } else {
                        System.out.println("Xóa khách hàng thất bại.");
                    }
                    break;

                case 4:
                    List<KhachHang> allKhachHang = kh_dao.findAll();
                    System.out.println("Danh sách khách hàng:");
                    allKhachHang.forEach(kh -> System.out.println(kh.getIdKH() + " - " + kh.getHoTen() + " - " + kh.getSdt() + " - " + kh.getHangMuc()));
                    break;

                case 5:
                    System.out.print("Nhập tên khách hàng cần tìm: ");
                    String tenKHSearch = scanner.nextLine();
                    List<KhachHang> foundKhachHangByName = kh_dao.findByName(tenKHSearch);
                    System.out.println("Kết quả tìm kiếm:");
                    foundKhachHangByName.forEach(kh -> System.out.println(kh.getIdKH() + " - " + kh.getHoTen() + " - " + kh.getSdt() + " - " + kh.getHangMuc()));
                    break;

                case 6:
                    System.out.print("Nhập mã khách hàng cần tìm: ");
                    String maKHSearch = scanner.nextLine();
                    KhachHang foundKhachHangById = kh_dao.findById(maKHSearch);
                    if (foundKhachHangById != null) {
                        System.out.println("Thông tin khách hàng:");
                        System.out.println(foundKhachHangById.getIdKH() + " - " + foundKhachHangById.getHoTen() + " - " + foundKhachHangById.getSdt() + " - " + foundKhachHangById.getHangMuc());
                    } else {
                        System.out.println("Không tìm thấy khách hàng với mã " + maKHSearch);
                    }
                    break;

                case 0:
                    System.out.println("Kết thúc");
                    em.close();
                    scanner.close();
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }
}