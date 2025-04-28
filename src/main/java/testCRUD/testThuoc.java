package testCRUD;

import dao.Thuoc_dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import entity.Thuoc;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class testThuoc {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
        Thuoc_dao thuoc_dao = new Thuoc_dao(em);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU THUỐC =====");
            System.out.println("1. Thêm thuốc");
            System.out.println("2. Cập nhật thuốc");
            System.out.println("3. Xóa thuốc");
            System.out.println("4. Hiển thị tất cả thuốc");
            System.out.println("5. Tìm thuốc theo tên");
            System.out.println("6. Tìm thuốc theo mã");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    Thuoc newThuoc = new Thuoc();
                    System.out.print("Nhập mã thuốc: ");
                    newThuoc.setIdThuoc(scanner.nextLine());

                    System.out.print("Nhập tên thuốc: ");
                    newThuoc.setTenThuoc(scanner.nextLine());

                    System.out.print("Nhập thành phần: ");
                    newThuoc.setThanhPhan(scanner.nextLine());

                    System.out.print("Nhập số lượng tồn: ");
                    newThuoc.setSoLuongTon(scanner.nextInt());

                    System.out.print("Nhập giá nhập: ");
                    newThuoc.setGiaNhap(scanner.nextDouble());

                    System.out.print("Nhập đơn giá: ");
                    newThuoc.setDonGia(scanner.nextDouble());

                    System.out.print("Nhập hạn sử dụng (yyyy-mm-dd): ");
                    scanner.nextLine(); // Clear buffer
                    newThuoc.setHanSuDung(LocalDate.parse(scanner.nextLine()));

                    // Bạn cần thêm logic lấy DonViTinh, DanhMuc, XuatXu nếu có quan hệ.
                    if (thuoc_dao.save(newThuoc)) {
                        System.out.println("Thêm thuốc thành công.");
                    } else {
                        System.out.println("Thêm thuốc thất bại.");
                    }
                    break;

                case 2:
                    System.out.print("Nhập mã thuốc cần cập nhật: ");
                    String maThuocUpdate = scanner.nextLine();
                    Thuoc thuocUpdate = thuoc_dao.findById(maThuocUpdate);
                    if (thuocUpdate != null) {
                        System.out.print("Nhập tên mới: ");
                        thuocUpdate.setTenThuoc(scanner.nextLine());

                        System.out.print("Nhập thành phần mới: ");
                        thuocUpdate.setThanhPhan(scanner.nextLine());

                        System.out.print("Nhập số lượng tồn mới: ");
                        thuocUpdate.setSoLuongTon(scanner.nextInt());

                        System.out.print("Nhập giá nhập mới: ");
                        thuocUpdate.setGiaNhap(scanner.nextDouble());

                        System.out.print("Nhập đơn giá mới: ");
                        thuocUpdate.setDonGia(scanner.nextDouble());

                        System.out.print("Nhập hạn sử dụng mới (yyyy-mm-dd): ");
                        scanner.nextLine(); // Clear buffer
                        thuocUpdate.setHanSuDung(LocalDate.parse(scanner.nextLine()));

                        if (thuoc_dao.update(thuocUpdate)) {
                            System.out.println("Cập nhật thuốc thành công.");
                        } else {
                            System.out.println("Cập nhật thuốc thất bại.");
                        }
                    } else {
                        System.out.println("Không tìm thấy thuốc với mã " + maThuocUpdate);
                    }
                    break;

                case 3:
                    System.out.print("Nhập mã thuốc cần xóa: ");
                    String maThuocDelete = scanner.nextLine();
                    if (thuoc_dao.delete(maThuocDelete)) {
                        System.out.println("Xóa thuốc thành công.");
                    } else {
                        System.out.println("Xóa thuốc thất bại.");
                    }
                    break;

                case 4:
                    List<Thuoc> allThuoc = thuoc_dao.findAll();
                    System.out.println("Danh sách thuốc:");
                    allThuoc.forEach(thuoc -> System.out.println(thuoc.getIdThuoc() + " - " + thuoc.getTenThuoc() + " - " + thuoc.getThanhPhan() + " - " +
                            thuoc.getSoLuongTon() + " - " + thuoc.getGiaNhap() + " - " + thuoc.getDonGia() + " - " + thuoc.getHanSuDung()));
                    break;

                case 5:
                    System.out.print("Nhập tên thuốc cần tìm: ");
                    String tenThuocSearch = scanner.nextLine();
                    List<Thuoc> foundThuocByName = thuoc_dao.findByName(tenThuocSearch);
                    System.out.println("Kết quả tìm kiếm:");
                    foundThuocByName.forEach(thuoc -> System.out.println(thuoc.getIdThuoc() + " - " + thuoc.getTenThuoc() + " - " + thuoc.getThanhPhan() + " - " +
                            thuoc.getSoLuongTon() + " - " + thuoc.getGiaNhap() + " - " + thuoc.getDonGia() + " - " + thuoc.getHanSuDung()));
                    break;

                case 6:
                    System.out.print("Nhập mã thuốc cần tìm: ");
                    String maThuocSearch = scanner.nextLine();
                    Thuoc foundThuocById = thuoc_dao.findById(maThuocSearch);
                    if (foundThuocById != null) {
                        System.out.println("Thông tin thuốc:");
                        System.out.println(foundThuocById.getIdThuoc() + " - " + foundThuocById.getTenThuoc() + " - " + foundThuocById.getThanhPhan() + " - " +
                                foundThuocById.getSoLuongTon() + " - " + foundThuocById.getGiaNhap() + " - " + foundThuocById.getDonGia() + " - " + foundThuocById.getHanSuDung());
                    } else {
                        System.out.println("Không tìm thấy thuốc với mã " + maThuocSearch);
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
