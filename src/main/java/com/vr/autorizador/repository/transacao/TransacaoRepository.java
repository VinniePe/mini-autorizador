package com.vr.autorizador.repository.transacao;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vr.autorizador.domain.transacao.Transacao;

public interface TransacaoRepository  extends JpaRepository<Transacao, UUID>{

}
