package com.it.regwm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.regwm.entity.Employee;
import com.it.regwm.mapper.EmpolyeeMapper;
import com.it.regwm.service.EmpolyService;
import org.springframework.stereotype.Service;

@Service
public class EmpolyeeServiceImpl extends ServiceImpl<EmpolyeeMapper, Employee> implements EmpolyService {

}
