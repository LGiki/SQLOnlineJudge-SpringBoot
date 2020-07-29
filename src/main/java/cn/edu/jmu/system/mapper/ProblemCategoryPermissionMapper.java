package cn.edu.jmu.system.mapper;

import cn.edu.jmu.system.entity.ProblemCategoryPermission;
import cn.edu.jmu.system.entity.dto.ProblemCategoryPermissionDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ProblemCategoryPermissionMapper extends BaseMapper<ProblemCategoryPermission> {

    IPage<ProblemCategoryPermissionDto> selectByProblemCategoryId(Page<?> page, Integer problemCategoryId);

}
