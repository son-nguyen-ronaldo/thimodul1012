package com.company.view;
import com.company.controller.ContactsManagement;
import com.company.model.Contact;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuContact {

    public static Scanner scanner = new Scanner(System.in);
    public static ContactsManagement contactManagement = new ContactsManagement();
    public static Contact contact = new Contact();

    static {
        contactManagement.addNew(new Contact("0335208135", "Gia đình", "Nguyễn Trung Sơn", "Nam", "Bắc Giang", "26/06/99", "ntson262@gmail.com"));
        contactManagement.addNew(new Contact("0335208136", "Bạn", "Trần Trung Kiên", "Nam", "Hà Nội", "29/04/99", "ntson263@gmail.com"));
        contactManagement.addNew(new Contact("0335208137", "Đối tác", "Phạm Trung Đức", "Nữ", "Bắc Giang", "18/02/99", "ntson264@gmail.com"));
        contactManagement.addNew(new Contact("0335208134", "Gia đình", "Nguyễn Hữu Trọng", "Nam", "Hà Nội", "28/08/98", "ntson265@gmail.com"));
        contactManagement.addNew(new Contact("0335208139", "Bạn", "Nguyễn Văn Hoàng", "Nữ", "Bắc Ninh", "12/07/97", "ntson266@gmail.com"));
        contactManagement.addNew(new Contact("0335208140", "Công việc", "Nguyễn Hữu Hùng", "Nam", "Hà Nội", "14/05/98", "ntson267@gmail.com"));

    }

    public void run() {
        String choice;
        do {
            menuContact();
            choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    displayContactList();
                    break;
                }
                case "2": {
                    addContact();
                    break;
                }
                case "3": {
                    updateContact();
                    break;
                }
                case "4": {
                    deleteContact();
                    break;
                }
                case "5": {
                    searchContactByPhoneNumberOrName();
                    break;
                }
                case "6": {
                    readDataToFile();
                    break;
                }
                case "7": {
                    writeDataToFile();
                    break;
                }
                default: {
                    System.out.println("Không hợp lệ ! bạn hãy chọn lại");
                }
            }

        } while (!choice.equals("8"));
    }

    private void writeDataToFile() {
        System.out.println("Bạn có muốn ghi lại toàn bộ file không ??");
        System.out.println("Nhập Y để tiếp tục - Nhấn phím bất kì để quay lại");
        String check = scanner.nextLine();
        if (!check.equals("Y")) {
            return;
        }
        List<Contact> contactList = contactManagement.getContactList();
        try {
            FileWriter fileWriter = new FileWriter("contacts.csv");
            BufferedWriter bw = new BufferedWriter(fileWriter);
            for (Contact contact: contactList) {
                bw.write(String.valueOf(contact));
                bw.newLine();
            }
            bw.close();
            System.out.println("Ghi vào file thành công");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDataToFile() {
        try {
            FileReader fileReader = new FileReader("contacts.csv");
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(Arrays.toString(line.split(",")));
            }
            br.close();
        } catch (IOException ie) {
            System.err.println("Fie không tồn tại hoặc nội dung có lỗi!");
        }
    }

    private void searchContactByPhoneNumberOrName() {
        System.out.print("Nhập vào số điện thoại hoặc họ tên để tìm kiếm trong danh bạ: ");
        String check = scanner.nextLine();
        int indexNumberPhone = contactManagement.findByNumberPhone(check);
        int indexName = contactManagement.findByName(check);

        if(indexName != -1) {
            System.out.println("liên hệ mà bạn cần tìm");
            System.out.println(contactManagement.getContactList().get(indexName));
        } else if (indexNumberPhone != -1) {
            System.out.println("liên hệ mà bạn cần tìm");
            System.out.println(contactManagement.getContactList().get(indexNumberPhone));
        } else {
            System.out.println("Không tìm thấy liên hệ nào phù hợp");
        }
    }


    private void deleteContact() {
        System.out.println("Xóa liên hệ");
        System.out.print("Nhập số điện thoại của danh bạ mà bạn muốn xóa: ");
        String numberPhone = scanner.nextLine();
        int index = contactManagement.findByNumberPhone(numberPhone);
        if (index != -1) {
            System.out.println("Bạn có muốn xóa sản phẩm khỏi danh sách k??");
            System.out.println("Bấm Y để xóa, Bấm phím bất kỳ để thoát");
            String check = scanner.nextLine();
            if (check.equals("Y")) {
                contactManagement.removeByNumberPhone(numberPhone);
                System.out.println("Xóa thành công");
            }
        } else {
            System.out.println("Không tìm thấy mời bạn nhập lại");
            deleteContact();
        }
    }

    public void updateContact() {
        System.out.println("Sửa thông tin liên hệ");
        System.out.print("Nhập số điện thoại của danh bạ cần sửa: ");
        String phoneNumber = scanner.nextLine();
        int index = contactManagement.findByNumberPhone(phoneNumber);
        if (index != -1) {
            Contact contact = inputContact();
            contactManagement.updateByNumberPhone(phoneNumber, contact);
        } else {
            System.out.println("Không tìm thấy mời bạn nhập lại");
            updateContact();
        }
    }

    public void addContact() {
        Contact contact = inputContact();
        contactManagement.addNew(contact);
        System.out.println("Thêm mới thành công");
    }

    private Contact inputContact() {
        System.out.println("Thêm mới liên hệ");
        String contactGroup, name, gender, address, birthday;
        String numberPhone;
        String email;
        boolean isvalidNumber, isvalidEmail;
        int count = 0;
        do {
            if (count > 0) {
                System.out.println("Bạn nhập sai rồi vui lòng nhập lại");
            }
            System.out.println("Nhập số điện thoại: ");
            numberPhone = scanner.nextLine();
            isvalidNumber = contact.validateNumber(numberPhone);
            if (!isvalidNumber) {
                System.out.println("Hãy nhập số từ 0 đến 9");
            }
            System.out.print("Nhập nhóm của danh bạ: ");
            contactGroup = scanner.nextLine();
            if(contactGroup.equals("")) {
                System.out.println("Không được để trống");
            }
            System.out.print("Nhập họ tên: ");
            name = scanner.nextLine();
            if(name.equals("")) {
                System.out.println("Không được để trống");
            }
            System.out.print("Nhập giới tính: ");
            gender = scanner.nextLine();
            if(gender.equals("")) {
                System.out.println("Không được để trống");
            }
            System.out.print("Nhập địa chỉ: ");
            address = scanner.nextLine();
            if(address.equals("")) {
                System.out.println("Không được để trống");
            }
            System.out.print("Nhập ngày tháng năm sinh: ");
            birthday = scanner.nextLine();
            if(birthday.equals("")) {
                System.out.println("Không được để trống");
            }
            System.out.print("Nhập email: ");
            email = scanner.nextLine();
            isvalidEmail = contact.validateEmail(email);
            if (!isvalidEmail) {
                System.out.println("nhập email chưa đúng");
            }
            count++;
        } while (contactGroup.equals("") || name.equals("") || gender.equals("") || address.equals("") || birthday.equals("") || !isvalidEmail || !isvalidNumber);
        return new Contact(numberPhone, contactGroup, name, gender, address, birthday, email);
    }

    private void displayContactList() {
        contactManagement.showAll();
    }

    public void menuContact() {
        System.out.println("***** Quản lý danh bạ *****");
        System.out.println("Chọn chứ năng theo số");
        System.out.println("1. Xem danh sách");
        System.out.println("2. Thêm mới");
        System.out.println("3. Cập nhật");
        System.out.println("4. Xóa");
        System.out.println("5. Tìm kiếm");
        System.out.println("6. Đọc từ file");
        System.out.println("7. Ghi vào file");
        System.out.println("8. Thoát");
        System.out.print("Chọn chứ năng: ");
    }
}