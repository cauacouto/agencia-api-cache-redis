package com.coutodev.agencia_api.service;

import com.coutodev.agencia_api.domin.Agencia;
import com.coutodev.agencia_api.dto.AgenciaRequest;
import com.coutodev.agencia_api.repository.AgenciaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class AgenciaService {

    private final RedisTemplate<String,String> redisTemplate;
    private final AgenciaRepository agenciaRepository;
    private final ObjectMapper objectMapper;

    public AgenciaService(RedisTemplate<String,String> redisTemplate, AgenciaRepository agenciaRepository, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.agenciaRepository = agenciaRepository;
        this.objectMapper = new ObjectMapper();
    }

    public void CadastrarAgencia(AgenciaRequest request){

        Agencia agencia = new Agencia();
        agencia.setCnpj(request.cnpj());
        agencia.setNome(request.nome());
        agencia.setEndereco(request.endereco());
       agenciaRepository.save(agencia);

    }


    public Agencia buscarPorId(Integer id){
        String key = "agencia_" + id;

        Agencia agencia = buscarNoCache(key);

        if (agencia != null){
            return agencia;
        }
        return  buscarNoBanco(key,id);
    }


    private Agencia buscarNoBanco(String key,Integer id){
        Agencia agencia = agenciaRepository.findById(id).
                orElseThrow(()-> new RuntimeException("agencia nao encontrada"));

        try {
            log.info("setando agencia no cache, key: {}", key);
            String json = objectMapper.writeValueAsString(agencia);
            redisTemplate.opsForValue().set(
                    key,
                    json,
                    3600,
                    TimeUnit.SECONDS
                    );
            return agencia;
        }catch (Exception e){
            throw new RuntimeException("erro ao salvar agencia no cache",e);
        }
    }



  private Agencia buscarNoCache(String key){
     String json = redisTemplate.opsForValue().get(key);

      if (json != null) {
       try {
           log.info("Agência encotrada no cache");
       Agencia agencia = objectMapper.readValue(json, Agencia.class);

       return agencia;
       }catch (Exception e){
           throw  new RuntimeException("erro ao desserializar agencia do cache",e);
       }
      }
      return null;
  }

      @Transactional
    public void deletar(Integer id){
        String key = "agencia_" + id;
       Agencia agencia = agenciaRepository.findById(id).orElseThrow(()
                -> new RuntimeException("agencia nao encontrada"));
        agenciaRepository.delete(agencia);
        redisTemplate.delete(key);
    }
}
