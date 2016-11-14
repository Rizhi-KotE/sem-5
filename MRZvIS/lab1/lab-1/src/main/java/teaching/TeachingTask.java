package teaching;

import Jama.Matrix;
import dto.ResultTeaching;
import image_utils.SaveUtils;
import lombok.Data;
import mains.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class TeachingTask implements Callable<ResultTeaching> {
    private final NeuronsTeacher teacher;

    public TeachingTask(NeuronsTeacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public ResultTeaching call() throws Exception {
        teacher.runTeaching();
        return teacher.getResultTeaching();
    }
}
