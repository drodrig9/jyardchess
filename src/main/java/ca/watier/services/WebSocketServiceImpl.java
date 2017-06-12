/*
 *    Copyright 2014 - 2017 Yannick Watier
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ca.watier.services;

import ca.watier.enums.ChessEventMessage;
import ca.watier.enums.Side;
import ca.watier.responses.ChessEvent;
import ca.watier.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Created by yannick on 6/10/2017.
 */

@Service
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate template;

    @Autowired
    public WebSocketServiceImpl(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void fireSideChessEvent(String uuid, Side side, ChessEventMessage evtMessage, String message) {
        Assert.assertNotNull(side, evtMessage);
        Assert.assertNotEmpty(uuid);
        Assert.assertNotEmpty(message);

        template.convertAndSend("/topic/" + uuid + '/' + side, new ChessEvent(evtMessage, message));
    }

    public void firePrivateChessEvent(ChessEventMessage evtMessage, String message) {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        template.convertAndSend("/topic/" + sessionId, new ChessEvent(evtMessage, message));
    }


    public void fireGameChessEvent(String uuid, ChessEventMessage evtMessage, Object message) {
        template.convertAndSend("/topic/" + uuid, new ChessEvent(evtMessage, message));
    }
}