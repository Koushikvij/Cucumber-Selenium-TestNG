package utilities;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public class TestDataBuilder<DTO>
{
    private DTO dto;

    public TestDataBuilder(Class<DTO> dtoType) {
        try
        {
            this.dto = dtoType.getDeclaredConstructor().newInstance();
        }
        catch (InstantiationException | IllegalAccessException var3) {
            var3.printStackTrace();
        }
        catch(InvocationTargetException | NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }
    }

    public TestDataBuilder<DTO> with(Consumer<? super DTO> action) {
        action.accept(this.dto);
        return this;
    }

    public DTO build() {
        return this.dto;
    }


}
