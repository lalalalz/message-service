package kr.co.kwt.messagecore.message.application.port.out;

import kr.co.kwt.messagecore.message.domain.Body;
import kr.co.kwt.messagecore.message.domain.Header;

public interface TemplateEnginePort {

    Header render(Header templateHeader);

    Body render(Body templateBody);
}
