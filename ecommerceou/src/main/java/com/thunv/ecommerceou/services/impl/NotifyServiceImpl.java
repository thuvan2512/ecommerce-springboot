package com.thunv.ecommerceou.services.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.thunv.ecommerceou.models.NotificationEntity;
import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.services.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


@Service
public class NotifyServiceImpl implements NotifyService {
    @Override
    @Async
    public void pushNotify(String recipientID, String image, String title, String details, String type) throws RuntimeException {
        try {
            NotificationEntity notificationEntity = new NotificationEntity();
            notificationEntity.setTitle(title);
            notificationEntity.setImage(image);
            notificationEntity.setDetails(details);
            notificationEntity.setType(type);
            Firestore fireStore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionApiFuture = fireStore.collection(recipientID)
                    .document(UUID.randomUUID().toString()).set(notificationEntity);
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
                String title = "The agent you follow is about to post a new sale post";
                String detail = String.format("Agent '%s' is about to have a new sale post with title '%s'.",
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