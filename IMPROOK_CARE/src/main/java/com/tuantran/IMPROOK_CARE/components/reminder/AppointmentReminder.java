package com.tuantran.IMPROOK_CARE.components.reminder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tuantran.IMPROOK_CARE.components.email.MailService;
import com.tuantran.IMPROOK_CARE.dto.EmailDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.Schedule;

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
    private Environment environment;

    // @Scheduled(cron = "0 8 * * *") // Chạy mỗi ngày vào lúc 8 giờ sáng
    @Scheduled(cron = "*/5 * * * * *") // Mỗi 5 giây
    public void checkAppointments() {
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

        // System.out.println("ĐMM");

    }

    // private void sendReminder(Appointment appointment) {
    // // Gửi thông báo qua email, SMS, hoặc push notification
    // System.out.println("Nhắc tái khám: Bệnh nhân " + appointment.getPatientId() +
    // " có cuộc hẹn tái khám vào "
    // + appointment.getAppointmentDate());
    // }
}
