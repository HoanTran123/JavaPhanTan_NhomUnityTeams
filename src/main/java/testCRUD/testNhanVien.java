package testCRUD;

import dao.NhanVien_dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import entity.NhanVien;

import java.util.List;
import java.util.Scanner;

public class testNhanVien {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
        NhanVien_dao nv_dao = new NhanVien_dao(em);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU Nhân Viên =====");
            System.out.println("1. Thêm Nhân Viên");
            System.out.println("2. Cập nhật Nhân Viên");
            System.out.println("3. Xóa Nhân Viên");
            System.out.println("4. Hiển thị tất cả Nhân Viên");
            System.out.println("5. Tìm Nhân Viên theo tên");
            System.out.println("6. Tìm Nhân Viên theo mã");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    NhanVien newNV = new NhanVien();
                    System.out.print("Nhập mã Nhân Viên: ");
                    newNV.setIdNV(scanner.nextLine());

                    System.out.print("Nhập tên Nhân Viên: ");
                    newNV.setHoTen(scanner.nextLine());

                    System.out.print("Nhập số điện thoại: ");
                    newNV.setSdt(scanner.nextLine());

                    System.out.print("Nhập email: ");
                    newNV.setEmail(scanner.nextLine());
                    
                    System.out.print("Nhập Chức Vụ: ");
                    newNV.setChucVu(scanner.nextLine());
                    

                    if (nv_dao.save(newNV)) {
                        System.out.println("Thêm Nhân Viên thành công.");
                    } else {
                        System.out.println("Thêm Nhân Viên thất bại.");
                    }
                    break;

                case 2:
                    System.out.print("Nhập mã Nhân Viên cần cập nhật: ");
                    String maNVUpdate = scanner.nextLine();
                    NhanVien NhanVienUpdate = nv_dao.findById(maNVUpdate);
                    if (NhanVienUpdate != null) {
                        System.out.print("Nhập tên mới: ");
                        NhanVienUpdate.setHoTen(scanner.nextLine());

                        System.out.print("Nhập số điện thoại mới: ");
                        NhanVienUpdate.setSdt(scanner.nextLine());

                        System.out.print("Nhập email mới: ");
                        NhanVienUpdate.setEmail(scanner.nextLine());
                        
                        System.out.print("Nhập Chức vụ mới: ");
                        NhanVienUpdate.setEmail(scanner.nextLine());

                        if (nv_dao.update(NhanVienUpdate)) {
                            System.out.println("Cập nhật Nhân Viên thành công.");
                        } else {
                            System.out.println("Cập nhật Nhân Viên thất bại.");
                        }
                    } else {
                        System.out.println("nvông tìm thấy Nhân Viên với mã " + maNVUpdate);
                    }
                    break;

                case 3:
                    System.out.print("Nhập mã Nhân Viên cần xóa: ");
                    String manvDelete = scanner.nextLine();
                    if (nv_dao.delete(manvDelete)) {
                        System.out.println("Xóa Nhân Viên thành công.");
                    } else {
                        System.out.println("Xóa Nhân Viên thất bại.");
                    }
                    break;

                case 4:
                    List<NhanVien> allNhanVien = nv_dao.findAll();
                    System.out.println("Danh sách Nhân Viên:");
                    allNhanVien.forEach(nv -> System.out.println(nv.getIdNV() + " - " + nv.getHoTen() + " - " + nv.getSdt() + " - " + nv.getEmail() + " - " + nv.getChucVu()));
                    break;

                case 5:
                    System.out.print("Nhập tên Nhân Viên cần tìm: ");
                    String tennvSearch = scanner.nextLine();
                    List<NhanVien> foundNhanVienByName = nv_dao.findByName(tennvSearch);
                    System.out.println("Kết quả tìm kiếm:");
                    foundNhanVienByName.forEach(nv -> System.out.println(nv.getIdNV() + " - " + nv.getHoTen() + " - " + nv.getSdt() + " - " + nv.getEmail() +" - " + nv.getChucVu() ));
                    break;

                case 6:
                    System.out.print("Nhập mã Nhân Viên cần tìm: ");
                    String manvSearch = scanner.nextLine();
                    NhanVien foundNhanVienById = nv_dao.findById(manvSearch);
                    if (foundNhanVienById != null) {
                        System.out.println("Thông tin Nhân Viên:");
                        System.out.println(foundNhanVienById.getIdNV() + " - " + foundNhanVienById.getHoTen() + " - " + foundNhanVienById.getSdt() + " - " + foundNhanVienById.getEmail() + " - " + foundNhanVienById.getChucVu());
                    } else {
                        System.out.println("nvông tìm thấy Nhân Viên với mã " + manvSearch);
                    }
                    break;

                case 0:
                    System.out.println("Kết thúc");
                    em.close();
                    scanner.close();
                    return;

                default:
                    System.out.println("Lựa chọn nvông hợp lệ.");
                    break;
            }
        }
    }
}