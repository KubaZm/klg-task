package pl.klgsolutions.klgtask.index.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class IndexData {

    private List<String> objects;

    private List<String> people;
}
