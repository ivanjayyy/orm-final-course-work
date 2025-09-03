package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.PaymentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.CourseDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.PaymentDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.StudentDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.PaymentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Course;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Payment;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Student;

import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOTypes.PAYMENT);
    CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOTypes.COURSE);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOTypes.STUDENT);

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) {
        Student student = studentDAO.search(paymentDTO.getStudentId()).getFirst();
        Course course = courseDAO.search(paymentDTO.getCourseId()).getFirst();

        return paymentDAO.save(new Payment(paymentDTO.getPaymentId(),paymentDTO.isStatus(),student,course));
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) {
        Student student = studentDAO.search(paymentDTO.getStudentId()).getFirst();
        Course course = courseDAO.search(paymentDTO.getCourseId()).getFirst();

        return paymentDAO.update(new Payment(paymentDTO.getPaymentId(),paymentDTO.isStatus(),student,course));
    }

    @Override
    public boolean deletePayment(String id) {
        return paymentDAO.delete(id);
    }

    @Override
    public ArrayList<PaymentDTO> searchPayment(String id) {
        ArrayList<Payment> payments = paymentDAO.search(id);
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        for (Payment payment : payments) {
            paymentDTOS.add(new PaymentDTO(payment.getId(),payment.isStatus(),payment.getStudent().getId(),payment.getCourse().getId()));
        }
        return paymentDTOS;
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayments() {
        ArrayList<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        for (Payment payment : payments) {
            paymentDTOS.add(new PaymentDTO(payment.getId(),payment.isStatus(),payment.getStudent().getId(),payment.getCourse().getId()));
        }
        return paymentDTOS;
    }

    @Override
    public String getNextPaymentId() {
        return paymentDAO.getNextId();
    }
}
