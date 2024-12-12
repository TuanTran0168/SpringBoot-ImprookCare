package com.tuantran.IMPROOK_CARE.components.reminder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tuantran.IMPROOK_CARE.components.email.MailService;
import com.tuantran.IMPROOK_CARE.dto.EmailDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.MedicalSchedule;
import com.tuantran.IMPROOK_CARE.repository.MedicalScheduleRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Component
public class AppointmentReminder {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MailService mailService;

    @Autowired
    private MedicalScheduleRepository medicalScheduleRepository;

    @Autowired
    private Environment environment;

    @SuppressWarnings("unchecked")
    @Scheduled(cron = "0 0 8 * * *") // Chạy mỗi ngày vào lúc 8 giờ sáng
    // @Scheduled(cron = "*/5 * * * * *") // Mỗi 5 giây
    private void checkAppointments() {
        // Tìm các cuộc hẹn trong 24 giờ tới
        // String jpql = "SELECT s FROM Schedule s " +
        // "WHERE s.scheduleId IN (SELECT b.scheduleId FROM Booking b) " +
        // "AND s.date BETWEEN :today AND :tomorrow";

        String jpql = "SELECT b FROM Booking b " +
                "WHERE b.scheduleId IN (SELECT s.scheduleId FROM Schedule s " +
                "WHERE s.date BETWEEN :today AND :tomorrow)";

        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(3);

        Date nowDate = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date nowTomorrow = Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Query query = entityManager.createQuery(jpql);
        query.setParameter("today", nowDate);
        query.setParameter("tomorrow", nowTomorrow);

        List<Booking> bookings = query.getResultList();

        for (Booking booking : bookings) {
            System.out.println(
                    "ALO " + booking.getScheduleId().getDate() + " - " + booking.getProfilePatientId().getEmail()
                            + " - " + booking.getProfilePatientId().getProfilePatientId());

            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setMailFrom(this.environment.getProperty("spring.mail.username"));
            emailDTO.setMailTo(booking.getProfilePatientId().getEmail());
            emailDTO.setMailSubject("CHÀO BẠN, ĐÂY LÀ API 5 GIÂY GỬI 1 LẦN");
            emailDTO.setMailContent("ĐÂY LÀ API GỬI MAIL NHẮC BẠN ĐI KHÁM");

            this.mailService.sendEmail(emailDTO);
        }

    }

    // Hàm để trích xuất giờ và phút từ `Date`
    // Sat May 04 10:16:50 ICT 2024
    private String[] extractHourAndMinute(String date) {
        // Cắt lấy phần giờ và phút
        String[] timeParts = date.split(" "); // Tách theo khoảng trắng

        String timeOfDay = "";

        /*
         * Vì có 2 định dạng time khác nhau nên chia vầy
         * Loại 1: Sat May 04 10:16:50 ICT 2024
         * Loại 2: 2024-08-28 10:48:30.0
         */
        if (timeParts.length > 2) {
            timeOfDay = timeParts[3]; // Lấy phần thời gian (10:16:50)
        } else {
            timeOfDay = timeParts[1]; // Lấy phần thời gian (10:16:50)
        }

        // Lấy phần giờ và phút
        String[] hourAndMinute = timeOfDay.substring(0, 5).split(":"); // Lấy giờ và phút (10:16)

        return hourAndMinute;
    }

    public void sendGroupedReminders(List<MedicalSchedule> upcomingReminders) {
        // Tạo Map để nhóm các nhắc nhở theo email
        Map<String, List<MedicalSchedule>> remindersByEmail = new HashMap<>();

        // Nhóm các nhắc nhở theo email
        for (MedicalSchedule reminder : upcomingReminders) {
            String email = reminder.getEmail();
            if (!remindersByEmail.containsKey(email)) {
                remindersByEmail.put(email, new ArrayList<>());
            }
            remindersByEmail.get(email).add(reminder);
        }

        // Duyệt qua Map để tạo nội dung email và gửi email cho từng nhóm
        for (Map.Entry<String, List<MedicalSchedule>> entry : remindersByEmail.entrySet()) {
            String email = entry.getKey();
            List<MedicalSchedule> reminders = entry.getValue();

            StringBuilder contentBuilder = new StringBuilder();
            for (MedicalSchedule reminder : reminders) {
                String[] time = this.extractHourAndMinute(reminder.getCustomTime().toString());
                contentBuilder.append("Nhắc uống thuốc: ")
                        .append(reminder.getMedicineName())
                        .append(" vào lúc: ")
                        .append(time[0])
                        .append(" giờ ")
                        .append(time[1])
                        .append(" phút\n");
            }

            String content = contentBuilder.toString();

            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setMailFrom(this.environment.getProperty("spring.mail.username"));
            emailDTO.setMailTo(email);
            emailDTO.setMailSubject("NHẮC NHỞ UỐNG THUỐC");
            emailDTO.setMailContent(content);

            this.mailService.sendEmail(emailDTO);

            System.out.println("Gửi thông báo đến email: " + email);
            System.out.println(content);
        }
    }

    // @Scheduled(cron = "0 0 8 * * *") // Chạy mỗi ngày vào lúc 8 giờ sáng
    // @Scheduled(cron = "*/5 * * * * *") // Mỗi 5 giây
    @Scheduled(cron = "0 * * * * *") // Chạy mỗi phút (giây = 0)
    private void checkUpcomingMedicalReminders() {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Tính thời gian 30 phút sau
        LocalDateTime in30Minutes = now.plusMinutes(30);

        // Chuyển đổi thành kiểu `Date`
        Date in30MinutesDate = Date.from(in30Minutes.atZone(ZoneId.systemDefault()).toInstant());

        System.out.println(in30MinutesDate.getTime());
        System.out.println(in30MinutesDate.toString());

        // Chuỗi thời gian gốc
        String timeString = in30MinutesDate.toString();

        String[] hourAndMinute = this.extractHourAndMinute(timeString);

        // In ra phần giờ và phút
        System.out.println("Giờ và phút: " + hourAndMinute[0] + " - " + hourAndMinute[1]);

        // Truy vấn dữ liệu trong khoảng 30 phút tới, tạm thời chưa đụng tới startDate
        List<MedicalSchedule> upcomingReminders = this.medicalScheduleRepository.findByHourAndMinute(
                Integer.parseInt(hourAndMinute[0]),
                Integer.parseInt(hourAndMinute[1]));

        this.sendGroupedReminders(upcomingReminders);

        // Thực hiện hành động nếu có nhắc nhở uống thuốc trong 30 phút tới
        // if (!upcomingReminders.isEmpty()) {
        // for (MedicalSchedule reminder : upcomingReminders) {
        // // Gửi email, thông báo, hoặc thực hiện hành động khác
        // // Cái CustomTime là 2024-08-28 10:48:30.0 nên lỗi hàm Extract
        // String[] time =
        // this.extractHourAndMinute(reminder.getCustomTime().toString());
        // String content_temp = "Nhắc uống thuốc: " + reminder.getMedicineName() + "
        // vào lúc: " +
        // time[0] + " giờ " + time[1] + " phút" + " - Gửi thông báo đến email: "
        // + reminder.getEmail() + "\n";

        // String content = "Nhắc uống thuốc: " + reminder.getMedicineName() + " vào
        // lúc: " +
        // time[0] + " giờ " + time[1] + " phút \n";

        // System.out.println(content_temp);

        // EmailDTO emailDTO = new EmailDTO();
        // emailDTO.setMailFrom(this.environment.getProperty("spring.mail.username"));
        // emailDTO.setMailTo(reminder.getEmail());
        // emailDTO.setMailSubject("NHẮC NHỞ UỐNG THUỐC");
        // emailDTO.setMailContent(content);

        // this.mailService.sendEmail(emailDTO);
        // }
        // }
    }
}
