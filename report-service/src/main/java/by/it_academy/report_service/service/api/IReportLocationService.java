package by.it_academy.report_service.service.api;

import java.util.List;
import java.util.UUID;

public interface IReportLocationService<E, C> {

    E save(C report);

    E get(UUID uuid);

    List<E> get();

}
