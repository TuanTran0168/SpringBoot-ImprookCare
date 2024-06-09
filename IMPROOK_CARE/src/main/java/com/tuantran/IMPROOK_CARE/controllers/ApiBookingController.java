/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.components.UUID.UUIDGenerator;
import com.tuantran.IMPROOK_CARE.components.datetime.DateFormatComponent;
import com.tuantran.IMPROOK_CARE.dto.BookingDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.BookingStatus;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.models.TimeSlot;
import com.tuantran.IMPROOK_CARE.service.BookingService;
import com.tuantran.IMPROOK_CARE.service.BookingStatusService;
import com.tuantran.IMPROOK_CARE.service.ProfileDoctorService;
import com.tuantran.IMPROOK_CARE.service.ProfilePatientService;
import com.tuantran.IMPROOK_CARE.service.ScheduleService;

import jakarta.validation.Valid;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiBookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    UUIDGenerator uuidGenerator;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ProfilePatientService profilePatientService;

    @Autowired
    BookingStatusService bookingStatusService;

    @Autowired
    private Environment environment;

    @Autowired
    private ProfileDoctorService profileDoctorService;

    @Autowired
    DateFormatComponent dateFormatComponent;

    @PostMapping("/auth/add-booking/")
    @CrossOrigin
    public ResponseEntity<?> addBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        String message = "Có lỗi xảy ra!";
        try {
            Booking booking = new Booking();
            Optional<Schedule> scheduleOptional = this.scheduleService
                    .findScheduleByIdAndActiveTrueOptional(Integer.parseInt(bookingDTO.getScheduleId()));

            if (scheduleOptional.isPresent()) {
                Schedule schedule = scheduleOptional.get();
                booking.setScheduleId(schedule);

                schedule.setBooked(Boolean.TRUE);

                Optional<ProfilePatient> profilePatientOptional = this.profilePatientService
                        .findProfilePatientByProfilePatientIdAndActiveTrueOptional(
                                Integer.parseInt(bookingDTO.getProfilePatientId()));

                if (profilePatientOptional.isPresent()) {
                    ProfilePatient profilePatient = profilePatientOptional.get();
                    profilePatient.setIsLock(Boolean.TRUE);

                    booking.setProfilePatientId(profilePatient);

                    /*
                     * (Cơ chế mới)
                     * Status số 6 là chưa thanh toán - sẽ không hiện ở view bác sĩ
                     * Mặc định khi tạo sẽ là chưa thanh toán (status_id là 6)
                     * 
                     * Sau khi thanh toán sẽ chuyển status về 1 (Chờ bác sĩ xác nhận) - lúc này mới
                     * hiện ở bác sĩ.
                     * 
                     * Chuyển status về 1 khi thanh toán sẽ thực hiện ở 1 API khác
                     */

                    booking.setStatusId(this.bookingStatusService.findBookingStatusByStatusId(6));
                    booking.setCreatedDate(new Date());

                    booking.setBookingCancel(Boolean.FALSE);
                    booking.setActive(Boolean.TRUE);

                    return new ResponseEntity<>(this.bookingService.createBooking(booking, schedule, profilePatient),
                            HttpStatus.CREATED);
                } else {
                    message = "ProfilePatient[" + bookingDTO.getProfilePatientId() + "] không tồn tại!";
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                }

            } else {
                message = "Schedule[" + bookingDTO.getScheduleId() + "] không tồn tại!";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/booking-user-view/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getBookingForUserView(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        return new ResponseEntity<>(this.bookingService.getBookingForUserView(Integer.parseInt(userId)), HttpStatus.OK);
    }

    @PostMapping("/auth/booking-user-view-page/")
    @CrossOrigin
    public ResponseEntity<?> getBookingForUserViewPage(@RequestBody Map<String, String> params,
            @RequestParam("pageNumber") String pageNumber) {
        String userId = params.get("userId");
        String bookingStatusId = params.get("bookingStatusId");
        // String pageNumber = params.get("pageNumber");
        int defaultPageNumber = 0;
        Sort mySort = Sort.by("createdDate").descending();
        Pageable page = PageRequest.of(defaultPageNumber,
                Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
        if (pageNumber != null && !pageNumber.isEmpty()) {
            if (!pageNumber.equals("NaN")) {
                page = PageRequest.of(Integer.parseInt(pageNumber),
                        Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")),
                        mySort);
            }
        }

        if (bookingStatusId.contains("&")) {
            String[] bookingStatus = bookingStatusId.split("&");

            return new ResponseEntity<>(
                    this.bookingService.getBookingForUserViewDoubleStatus(Integer.parseInt(userId),
                            Integer.parseInt(bookingStatus[0]),
                            Integer.parseInt(bookingStatus[1]), page),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(this.bookingService.getBookingForUserView(Integer.parseInt(userId),
                Integer.parseInt(bookingStatusId), page), HttpStatus.OK);
    }

    @PostMapping("/public/time-slot-booking/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getTimeSlotsForDoctorOnDate(@RequestBody Map<String, String> params) {
        String profileDoctorId = params.get("profileDoctorId");
        String date = params.get("date");
        return new ResponseEntity<>(
                this.bookingService.getTimeSlotsForDoctorOnDate(Integer.parseInt(profileDoctorId), date),
                HttpStatus.OK);
    }

    @PostMapping("/public/date-booking/")
    @CrossOrigin
    public ResponseEntity<?> getDatesForProfileDoctor(@RequestBody Map<String, String> params) {
        String profileDoctorId = params.get("profileDoctorId");
        return new ResponseEntity<>(this.bookingService.getDatesForProfileDoctor(Integer.parseInt(profileDoctorId)),
                HttpStatus.OK);
    }

    @PostMapping("/auth/doctor/accept-booking/")
    @CrossOrigin
    public ResponseEntity<?> acceptBooking(@RequestBody String bookingId) {

        try {
            String message = "Có lỗi xảy ra!";
            Optional<Booking> bookingOptional = this.bookingService
                    .findBookingByBookingIdAndActiveTrue(Integer.parseInt(bookingId));

            if (bookingOptional.isPresent()) {
                Booking booking = bookingOptional.get();
                booking.setStatusId(new BookingStatus(2));
                booking.setUpdatedDate(new Date());
                // Chỗ này sau này quay lại thêm tiền tố url bên react cho cái uuid này
                booking.setLinkVideoCall(this.uuidGenerator.generateUUID());
                return new ResponseEntity<>(this.bookingService.acceptBooking(booking), HttpStatus.OK);
            } else {
                message = "Booking[" + bookingId + "] không tồn tại!";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/doctor/deny-booking/")
    @CrossOrigin
    public ResponseEntity<String> denyBooking(@RequestBody String bookingId) {

        String message = "Có lỗi xảy ra!";
        int check = this.bookingService.denyBooking(Integer.parseInt(bookingId));

        if (check == 1) {
            message = "Từ chối thành công lịch đặt khám!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        if (check == 0) {
            message = "Từ chối thất bại lịch đặt khám!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/cancel-booking/")
    @CrossOrigin
    public ResponseEntity<String> cancelBooking(@RequestBody String bookingId) {

        String message = "Có lỗi xảy ra!";
        int check = this.bookingService.cancelBooking(Integer.parseInt(bookingId));

        if (check == 1) {
            message = "Hủy thành công lịch đặt khám!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        if (check == 0) {
            message = "Hủy thất bại lịch đặt khám!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/booking-doctor-view/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getBookingForDoctorView(@RequestBody Map<String, String> params) {
        String profiledoctorId = params.get("profileDoctorId");
        return new ResponseEntity<>(this.bookingService.getBookingForDoctorView(Integer.parseInt(profiledoctorId)),
                HttpStatus.OK);
    }

    @PostMapping("/auth/booking-details-user-view/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getBookingDetailsByBookingId(@RequestBody Map<String, String> params) {
        String bookingId = params.get("bookingId");
        return new ResponseEntity<>(this.bookingService.getBookingDetailsByBookingId(Integer.parseInt(bookingId)),
                HttpStatus.OK);
    }

    @PostMapping("/auth/booking-doctor-view-page/")
    @CrossOrigin
    public ResponseEntity<?> getBookingForDoctorViewPage(@RequestBody Map<String, String> params) {
        try {
            String profileDoctorId = params.get("profileDoctorId");
            String bookingStatusId = params.get("bookingStatusId");
            return new ResponseEntity<>(this.bookingService.getBookingForDoctorViewPage(
                    Integer.parseInt(profileDoctorId), Integer.parseInt(bookingStatusId), params), HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Something wrong here, Internal Server Error!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * API này dùng để tạo lịch tái khám (Booking)
     * Gồm các bước:
     * - Tạo TimeSlot
     * - Tạo Schedule
     * - Tạo Booking
     * 
     */
    @PostMapping("/auth/create-booking-re-examination/")
    @CrossOrigin
    public ResponseEntity<?> createBookingReExamination(@Valid @RequestBody Map<String, String> params) {

        /*
         * createBookingReExamination cần: TimeSlot, Schedule, Booking, ProfilePatient
         * Không lưu bất kỳ Object nào tại đây để sang Service dùng Transaction
         */

        try {
            String message = "Có lỗi xảy ra!";
            String timeBegin = params.get("timeBegin");
            String timeEnd = params.get("timeEnd");
            String note = params.get("note");
            String profileDoctorId = params.get("profileDoctorId");
            String profilePatientId = params.get("profilePatientId");

            ProfileDoctor profileDoctor = this.profileDoctorService
                    .findProfileDoctorByProfileDoctorIdAndActiveTrue(Integer.parseInt(profileDoctorId));

            if (profileDoctor != null) {
                /*
                 * Tạo TimeSlot custom và Schedule
                 */
                Date timeBeginParse = dateFormatComponent.myDateTimeFormat().parse(timeBegin);
                Date timeEndParse = dateFormatComponent.myDateTimeFormat().parse(timeEnd);

                TimeSlot timeSlot = new TimeSlot();

                timeSlot.setTimeBegin(timeBeginParse);
                timeSlot.setTimeEnd(timeEndParse);
                timeSlot.setNote(note);
                timeSlot.setProfileDoctorId(profileDoctor);
                timeSlot.setActive(Boolean.TRUE);
                timeSlot.setCreatedDate(new Date());

                /*
                 * Schedule này chưa set id của TimeSlot vì TimeSlot chưa lưu nên không có id
                 */
                Schedule schedule = new Schedule();

                schedule.setProfileDoctorId(profileDoctor);
                schedule.setDate(this.dateFormatComponent.myDateFormat().parse(timeBegin));
                schedule.setCreatedDate(new Date());
                schedule.setActive(Boolean.TRUE);
                schedule.setBooked(Boolean.FALSE);

                /*
                 * Không dùng được phương thức cũ vì nó sẽ lưu luôn xuống database kể cả các
                 * bước sau fail
                 */
                // Schedule schedule =
                // this.timeSlotService.addTimeSlotAndSchedule(timeBeginParse, timeEndParse,
                // note, profileDoctor);

                /*
                 * Booking này chưa set id của Schedule vì Schedule chưa lưu
                 */
                Booking booking = new Booking();

                schedule.setBooked(Boolean.TRUE);
                Optional<ProfilePatient> profilePatientOptional = this.profilePatientService
                        .findProfilePatientByProfilePatientIdAndActiveTrueOptional(
                                Integer.parseInt(profilePatientId));

                if (profilePatientOptional.isPresent()) {
                    ProfilePatient profilePatient = profilePatientOptional.get();
                    profilePatient.setIsLock(Boolean.TRUE);
                    booking.setProfilePatientId(profilePatient);
                    booking.setStatusId(this.bookingStatusService.findBookingStatusByStatusId(5));
                    booking.setCreatedDate(new Date());
                    booking.setBookingCancel(Boolean.FALSE);
                    booking.setActive(Boolean.TRUE);

                    return new ResponseEntity<>(
                            this.bookingService.createBookingReExamination(timeSlot, booking, schedule,
                                    profilePatient),
                            HttpStatus.CREATED);

                } else {
                    message = "ProfilePatient[" + profilePatientId + "] không tồn tại!";
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                }

            } else {
                message = "ProfileDoctor[" + profileDoctorId + "] không tồn tại!";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
}
