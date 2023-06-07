package com.thunv.ecommerceou.services.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.thunv.ecommerceou.models.NotificationEntity;
import com.thunv.ecommerceou.models.enumerate.NotificationImages;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.models.pojo.PromotionProgram;
import com.thunv.ecommerceou.services.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


@Service
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private Environment env;
    @Override
    @Async
    public void pushNotify(String recipientID, String image, String title, String details, String type) throws RuntimeException {
        try {
            int disableNotify = Integer.parseInt(this.env.getProperty("firebase.cloud_storage.disable_notify"));
            if (disableNotify == 0){
                NotificationEntity notificationEntity = new NotificationEntity();
                notificationEntity.setTitle(title);
                notificationEntity.setImage(image);
                notificationEntity.setDetails(details);
                notificationEntity.setType(type);
                Firestore fireStore = FirestoreClient.getFirestore();
                ApiFuture<WriteResult> collectionApiFuture = fireStore.collection(recipientID)
                        .document(UUID.randomUUID().toString()).set(notificationEntity);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Async
    public void pushListFollowNotifyForUser(List<FollowAgency> followAgencyList, String titlePost) {
        try {
            for (FollowAgency followAgency: followAgencyList){
                String recipient = String.format("user-%s", followAgency.getAuthor().getId());
                String title = "The merchant you follow is about to post a new sale post";
                String detail = String.format("Merchant '%s' is about to have a new sale post with title '%s'.",
                        followAgency.getAgency().getName(), titlePost);
                String type = "New Sale Post";
                this.pushNotify(recipient, followAgency.getAgency().getAvatar(), title, detail, type);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Async
    public void pushListFollowNotifyPromotionForUser(List<FollowAgency> followAgencyList, PromotionProgram promotionProgram) {
        try {
            for (FollowAgency followAgency: followAgencyList){
                String recipient = String.format("user-%s", followAgency.getAuthor().getId());
                String title = "The merchant you follow is about to post a new promotion program";
                String detail = String.format("Merchant '%s' is about to have a new promotion program with title '%s'. View detail at merchant's page now !!!",
                        followAgency.getAgency().getName(), promotionProgram.getProgramName());
                String type = "New Promotion Program";
                this.pushNotify(recipient, followAgency.getAgency().getAvatar(), title, detail, type);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Async
    public void pushListBanAgencyNotifyForManager(List<Agency> agencyList) {
        try {
            for (Agency agency: agencyList){
                String recipient = String.format("agency-%s", agency.getId());
                String title = "Your agent has been deactivated";
                String detail = "Your agent has been deactivated for not renewing the package.";
                String type = "Deactivated";
                this.pushNotify(recipient, NotificationImages.BAN_AGENCY.getValue(), title, detail, type);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Async
    public void pushListNotifyRemindAgency(List<Agency> agencyList) {
        try {
            for (Agency agency: agencyList){
                String recipient = String.format("agency-%s", agency.getId());
                String title = "Your agent is about to be disabled";
                String detail = "Your agent is about to be disabled due to not renewing the package";
                String type = "Remind renewal package";
                this.pushNotify(recipient, NotificationImages.BAN_AGENCY.getValue(), title, detail, type);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Async
    public void updateSeenStatusOfNotify(String recipientID) throws RuntimeException{
        try {
            Firestore fireStore = FirestoreClient.getFirestore();
            Iterable<DocumentReference> listDocRef = fireStore.collection(recipientID).listDocuments();
            for (DocumentReference document : listDocRef){
                // (async) Update one field
                ApiFuture<WriteResult> future = document.update("seen", true);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }
}
