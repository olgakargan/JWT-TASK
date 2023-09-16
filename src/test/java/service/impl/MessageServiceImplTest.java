package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.example.jwttask.dto.MessageRequest;
import com.example.jwttask.dto.MessageResponse;
import com.example.jwttask.entity.Message;
import com.example.jwttask.entity.User;
import com.example.jwttask.repository.MessageRepository;
import com.example.jwttask.repository.UserRepository;
import com.example.jwttask.service.impl.MessageServiceImpl;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MessageServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MessageServiceImplTest {
    @MockBean
    private MessageRepository messageRepository;

    @Autowired
    private MessageServiceImpl messageServiceImpl;

    @MockBean
    private UserRepository userRepository;


    @Test
    void testNewMessage2() {
        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        Message message = new Message();
        message.setId(1L);
        message.setText("Text");
        message.setUser(user);
        when(messageRepository.save(Mockito.any())).thenReturn(message);

        User user2 = new User();
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findUserByUsername(Mockito.any())).thenReturn(ofResult);
        MessageRequest message2 = mock(MessageRequest.class);
        when(message2.getUsername()).thenReturn("janedoe");
        when(message2.getMessage()).thenReturn("Not all who wander are lost");
        Optional<MessageResponse> actualNewMessageResult = messageServiceImpl.newMessage(message2);
        assertTrue(actualNewMessageResult.isPresent());
        assertEquals(1, actualNewMessageResult.get().getMessages().size());
        verify(messageRepository).save(Mockito.any());
        verify(userRepository).findUserByUsername(Mockito.any());
        verify(message2, atLeast(1)).getMessage();
        verify(message2).getUsername();
    }

    @Test
    void testNewMessage3() {
        MessageRequest message = mock(MessageRequest.class);
        when(message.getUsername()).thenThrow(new EntityNotFoundException("An error occurred"));
        when(message.getMessage()).thenReturn("Not all who wander are lost");
        assertThrows(EntityNotFoundException.class, () -> messageServiceImpl.newMessage(message));
        verify(message).getMessage();
        verify(message).getUsername();
    }

    @Test
    void testNewMessage4() {
        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        Message message = mock(Message.class);
        when(message.getText()).thenReturn("Text");
        doNothing().when(message).setId(anyLong());
        doNothing().when(message).setText(Mockito.any());
        doNothing().when(message).setUser(Mockito.any());
        message.setId(1L);
        message.setText("Text");
        message.setUser(user);
        when(messageRepository.save(Mockito.any())).thenReturn(message);

        User user2 = new User();
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findUserByUsername(Mockito.any())).thenReturn(ofResult);
        MessageRequest message2 = mock(MessageRequest.class);
        when(message2.getUsername()).thenReturn("janedoe");
        when(message2.getMessage()).thenReturn("Not all who wander are lost");
        Optional<MessageResponse> actualNewMessageResult = messageServiceImpl.newMessage(message2);
        assertTrue(actualNewMessageResult.isPresent());
        assertEquals(1, actualNewMessageResult.get().getMessages().size());
        verify(messageRepository).save(Mockito.any());
        verify(message).getText();
        verify(message).setId(anyLong());
        verify(message).setText(Mockito.any());
        verify(message).setUser(Mockito.any());
        verify(userRepository).findUserByUsername(Mockito.any());
        verify(message2, atLeast(1)).getMessage();
        verify(message2).getUsername();
    }

    @Test
    void testNewMessage5() {
        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        Message message = mock(Message.class);
        doNothing().when(message).setId(anyLong());
        doNothing().when(message).setText(Mockito.any());
        doNothing().when(message).setUser(Mockito.any());
        message.setId(1L);
        message.setText("Text");
        message.setUser(user);
        when(userRepository.findUserByUsername(Mockito.any())).thenReturn(Optional.empty());
        MessageRequest message2 = mock(MessageRequest.class);
        when(message2.getUsername()).thenReturn("janedoe");
        when(message2.getMessage()).thenReturn("Not all who wander are lost");
        assertThrows(EntityNotFoundException.class, () -> messageServiceImpl.newMessage(message2));
        verify(message).setId(anyLong());
        verify(message).setText(Mockito.any());
        verify(message).setUser(Mockito.any());
        verify(userRepository).findUserByUsername(Mockito.any());
        verify(message2).getMessage();
        verify(message2, atLeast(1)).getUsername();
    }
}