@echo off

call mvn -T %NUMBER_OF_PROCESSORS% clean install package dependency:copy-dependencies generate-sources

@echo on