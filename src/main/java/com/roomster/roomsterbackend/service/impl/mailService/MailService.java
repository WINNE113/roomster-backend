package com.roomster.roomsterbackend.service.impl.mailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.roomster.roomsterbackend.entity.InforRoomEntity;
import com.roomster.roomsterbackend.entity.OrderEntity;
import com.roomster.roomsterbackend.entity.RoomServiceEntity;
import com.roomster.roomsterbackend.entity.TenantEntity;
import com.roomster.roomsterbackend.repository.RoomRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private RoomRepository roomRepository;

    public void sendSimpleEmail(TenantEntity tenant, String subject, String text, String name, OrderEntity order) {
        try {
        	Long roomId = order.getRoomId();
        	
        	Optional<InforRoomEntity> room = roomRepository.findById(roomId);
        	
        	InforRoomEntity roomRs = room.get();
        	
        	BigDecimal priceWate = roomRs.getWaterPrice().multiply(order.getWater());
        	
        	BigDecimal priceElectric = roomRs.getElectricityPrice().multiply(order.getElectricity());
        	
        	BigDecimal priceService = BigDecimal.ZERO;
        	
        	
        	
        	
        	for (RoomServiceEntity roomServicePrice : roomRs.getServices()) {
				priceService = priceService.add(roomServicePrice.getServiceHouse().getServicePrice());
			}
        	
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(tenant.getEmail());
            helper.setSubject(subject);

            // Read HTML content from the file
            String templateContent = readHtmlContent("email_template.html");

            // Replace placeholders with actual values
            templateContent = templateContent.replace("{name}", name);
            templateContent = templateContent.replace("{phone}", tenant.getPhoneNumber());
            templateContent = templateContent.replace("{numberRoom}", String.valueOf(roomRs.getNumberRoom()));
            templateContent = templateContent.replace("{total}", String.valueOf(order.getTotal()));
            templateContent = templateContent.replace("{totalPayment}", String.valueOf(order.getTotalPayment()));
            templateContent = templateContent.replace("{rest}", String.valueOf(order.getTotal().subtract(order.getTotalPayment())));
            templateContent = templateContent.replace("{priceWate}", String.valueOf(priceWate));
            templateContent = templateContent.replace("{priceElectric}", String.valueOf(priceElectric));
            templateContent = templateContent.replace("{priceService}", String.valueOf(priceService));
            

            helper.setText(templateContent, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    private String readHtmlContent(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readString(path);
    }
}