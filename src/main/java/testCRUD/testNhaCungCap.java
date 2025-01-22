package testCRUD;

import dao.NhaCungCap_dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import entity.NhaCungCap;

import java.util.List;
import java.util.Scanner;

public class testNhaCungCap {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
        NhaCungCap_dao ncc_dao = new NhaCungCap_dao(em);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU NHÀ CUNG CẤP =====");
            System.out.println("1. Thêm nhà cung cấp");
            System.out.println("2. Cập nhật nhà cung cấp");
            System.out.println("3. Xóa nhà cung cấp");
            System.out.println("4. Hiển thị tất cả nhà cung cấp");
            System.out.println("5. Tìm nhà cung cấp theo tên");
            System.out.println("6. Tìm nhà cung cấp theo mã");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    NhaCungCap newNcc = new NhaCungCap();
                    System.out.print("Nhập mã nhà cung cấp: ");
                    newNcc.setIdNCC(scanner.nextLine());

                    System.out.print("Nhập tên nhà cung cấp: ");
                    newNcc.setTenNCC(scanner.nextLine());

                    System.out.print("Nhập số điện thoại: ");
                    newNcc.setSdt(scanner.nextLine());

                    System.out.print("Nhập địa chỉ: ");
                    newNcc.setDiaChi(scanner.nextLine());

                    if (ncc_dao.save(newNcc)) {
                        System.out.println("Thêm nhà cung cấp thành công.");
                    } else {
                        System.out.println("Thêm nhà cung cấp thất bại.");
                    }
                    break;

                case 2:
                    System.out.print("Nhập mã nhà cung cấp cần cập nhật: ");
                    String maNCCUpdate = scanner.nextLine();
                    NhaCungCap nhaCungCapUpdate = ncc_dao.findById(maNCCUpdate);
                    if (nhaCungCapUpdate != null) {
                        System.out.print("Nhập tên mới: ");
                        nhaCungCapUpdate.setTenNCC(scanner.nextLine());

                        System.out.print("Nhập số điện thoại mới: ");
                        nhaCungCapUpdate.setSdt(scanner.nextLine());

                        System.out.print("Nhập địa chỉ mới: ");
                        nhaCungCapUpdate.setDiaChi(scanner.nextLine());

                        if (ncc_dao.update(nhaCungCapUpdate)) {
                            System.out.println("Cập nhật nhà cung cấp thành công.");
                        } else {
                            System.out.println("Cập nhật nhà cung cấp thất bại.");
                        }
                    } else {
                        System.out.println("Không tìm thấy nhà cung cấp với mã " + maNCCUpdate);
                    }
                    break;

                case 3:
                    System.out.print("Nhập mã nhà cung cấp cần xóa: ");
                    String maNCCDelete = scanner.nextLine();
                    if (ncc_dao.delete(maNCCDelete)) {
                        System.out.println("Xóa nhà cung cấp thành công.");
                    } else {
                        System.out.println("Xóa nhà cung cấp thất bại.");
                    }
                    break;

                case 4:
                    List<NhaCungCap> allNhaCungCap = ncc_dao.findAll();
                    System.out.println("Danh sách nhà cung cấp:");
                    allNhaCungCap.forEach(ncc -> System.out.println(ncc.getIdNCC() + " - " + ncc.getTenNCC() + " - " + ncc.getSdt() + " - " + ncc.getDiaChi()));
                    break;

                case 5:
                    System.out.print("Nhập tên nhà cung cấp cần tìm: ");
                    String tenNCCSearch = scanner.nextLine();
                    List<NhaCungCap> foundNhaCungCapByName = ncc_dao.findByName(tenNCCSearch);
                    System.out.println("Kết quả tìm kiếm:");
                    foundNhaCungCapByName.forEach(ncc -> System.out.println(ncc.getIdNCC() + " - " + ncc.getTenNCC() + " - " + ncc.getSdt() + " - " + ncc.getDiaChi()));
                    break;

                case 6:
                    System.out.print("Nhập mã nhà cung cấp cần tìm: ");
                    String maNCCSearch = scanner.nextLine();
                    NhaCungCap foundNhaCungCapById = ncc_dao.findById(maNCCSearch);
                    if (foundNhaCungCapById != null) {
                        System.out.println("Thông tin nhà cung cấp:");
                        System.out.println(foundNhaCungCapById.getIdNCC() + " - " + foundNhaCungCapById.getTenNCC() + " - " + foundNhaCungCapById.getSdt() + " - " + foundNhaCungCapById.getDiaChi());
                    } else {
                        System.out.println("Không tìm thấy nhà cung cấp với mã " + maNCCSearch);
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
