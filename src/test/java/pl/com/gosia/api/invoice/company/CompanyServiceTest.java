package pl.com.gosia.api.invoice.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.com.gosia.api.invoice.company.dto.NewCompany;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CompanyServiceTest {

    private CompanyService companyService;

    private final CompanyRepository companyRepository = mock(CompanyRepository.class);
    private final CompanyApiClient companyApiClient = mock(CompanyApiClient.class);



    @BeforeEach
    void setUp() {
        companyService = new CompanyService(companyRepository, companyApiClient);

    }

    @Test
    void findCompany() {
        // given
        final var mockedNewCompany = NewCompany.builder().companyName("Aad").nip("5222450829").adress("aaaaa").build();
        when(companyRepository.findCompanyByNip(anyString())).thenReturn(Optional.empty());
        when(companyApiClient.findCompanyWithApi(anyString())).thenReturn(mockedNewCompany);

        // when
        final var company = companyService.findCompany("5222450829");

        // then
        verify(companyApiClient).findCompanyWithApi("5222450829");
        verify(companyRepository).saveCompany(mockedNewCompany);
    }




}