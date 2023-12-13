package com.roomster.roomsterbackend.service.IService;

import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface ITransactionService {
    ResponseEntity<?> purchasePackageByUser(Principal connectedUser, Long servicePackageId);

    ResponseEntity<?> isValidUltiManager(Principal connectedUser);

    ResponseEntity<?> purchasedServiceByUser(Principal connectedUser);
}
