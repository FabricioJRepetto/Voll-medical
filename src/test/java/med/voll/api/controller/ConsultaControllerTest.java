package med.voll.api.controller;

import med.voll.api.dominio.consulta.ConsultaService;
import med.voll.api.dominio.consulta.DatosAgendarConsulta;
import med.voll.api.dominio.consulta.DatosDetalleConsulta;
import med.voll.api.dominio.medico.Especialidad;
import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DatosAgendarConsulta> consultaJacksonTester;
    @Autowired
    private JacksonTester<DatosDetalleConsulta> detalleConsultaJacksonTester;

    @MockBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deberia retornar erro 400 cuando los datos ingresados sean invalidos")
    @WithMockUser
    void agendarEscenario1() throws Exception {
        //given
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        //then
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deberia retornar http 200 cuando los datos ingresados sean validos")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        //given
        var fecha = LocalDateTime.now().plusHours(1);
        var dto = new DatosAgendarConsulta(null, 2L, 5L, fecha, Especialidad.CARDIOLOGIA);
        var dtoEsperado = new DatosDetalleConsulta(null, 2L, 5L, fecha);

        //when
        when(consultaService.agendar(any())).thenReturn(dtoEsperado);

        var response = mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(consultaJacksonTester.write(dto).getJson())
        ).andReturn().getResponse();

        //then
        assertEquals(response.getStatus(), HttpStatus.OK.value());


        var jsonExpected = detalleConsultaJacksonTester.write(dtoEsperado).getJson();

        assertEquals(response.getContentAsString(), jsonExpected);
    }
}